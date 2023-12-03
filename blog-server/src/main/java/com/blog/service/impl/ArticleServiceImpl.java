package com.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.api.dto.article.*;
import com.api.dto.base.PageResultDTO;
import com.api.dto.other.TopAndFeaturedArticlesDTO;
import com.api.enums.FileExtEnum;
import com.api.enums.FilePathEnum;
import com.api.vo.article.ArticlePasswordVO;
import com.api.vo.article.ArticleTopFeaturedVO;
import com.api.vo.article.ArticleVO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.DeleteVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.repo.ArticleRepo;
import com.blog.service.ArticleService;
import com.blog.service.ArticleTagService;
import com.blog.service.RedisService;
import com.blog.service.TagService;
import com.blog.strategy.context.SearchStrategyContext;
import com.blog.strategy.context.UploadStrategyContext;
import com.core.exception.BizException;
import com.core.mapper.ArticleMapper;
import com.core.mapper.ArticleTagMapper;
import com.core.mapper.CategoryMapper;
import com.core.mapper.TagMapper;
import com.core.modle.entity.Article;
import com.core.modle.entity.ArticleTag;
import com.core.modle.entity.Category;
import com.core.modle.entity.Tag;
import com.core.modle.es.ArticleSearch;
import com.core.util.BeanCopyUtil;
import com.core.util.PageUtil;
import com.core.util.UserUtil;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.api.constant.RabbitMQConstant.SUBSCRIBE_EXCHANGE;
import static com.api.constant.RedisConstant.ARTICLE_ACCESS;
import static com.api.constant.RedisConstant.ARTICLE_VIEWS_COUNT;
import static com.api.enums.ArticleStatusEnum.DRAFT;
import static com.api.enums.StatusCodeEnum.ARTICLE_ACCESS_FAIL;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private ArticleRepo articleRepo;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private TagService tagService;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private RedisService redisService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private UploadStrategyContext uploadStrategyContext;

    @Resource
    private SearchStrategyContext searchStrategyContext;

    @SneakyThrows
    @Override
    public TopAndFeaturedArticlesDTO listTopAndFeaturedArticles() {
        List<ArticleCardDTO> list = articleMapper.listTopAndFeaturedArticles();
        if (list.isEmpty()) {
            return new TopAndFeaturedArticlesDTO();
        } else if (list.size() > 3) {
            list = list.subList(0, 3);
        }
        TopAndFeaturedArticlesDTO topAndFeaturedArticlesDTO = new TopAndFeaturedArticlesDTO();
        topAndFeaturedArticlesDTO.setTopArticle(list.get(0));
        list.remove(0);
        topAndFeaturedArticlesDTO.setFeaturedArticles(list);
        return topAndFeaturedArticlesDTO;
    }

    @SneakyThrows
    @Override
    public PageResultDTO<ArticleCardDTO> listArticles() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, 0)
                .in(Article::getStatus, 1, 2);
        CompletableFuture<Integer> asyncCount = CompletableFuture.supplyAsync(() -> Math.toIntExact(articleMapper.selectCount(queryWrapper)));
        List<ArticleCardDTO> articles = articleMapper.listArticles(PageUtil.getLimitCurrent(), PageUtil.getSize());
        return new PageResultDTO<>(articles, asyncCount.get());
    }

    @SneakyThrows
    @Override
    public PageResultDTO<ArticleCardDTO> listArticlesByCategoryId(Integer categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>().eq(Article::getCategoryId, categoryId);
        CompletableFuture<Integer> asyncCount = CompletableFuture.supplyAsync(() -> Math.toIntExact(articleMapper.selectCount(queryWrapper)));
        List<ArticleCardDTO> articles = articleMapper.getArticlesByCategoryId(PageUtil.getLimitCurrent(), PageUtil.getSize(), categoryId);
        return new PageResultDTO<>(articles, asyncCount.get());
    }

    @SneakyThrows
    @Override
    public ArticleDTO getArticleById(Integer articleId) {
        Article articleForCheck = articleMapper.selectOne(new LambdaQueryWrapper<Article>().eq(Article::getId, articleId));
        if (Objects.isNull(articleForCheck)) {
            return null;
        }
        if (articleForCheck.getStatus().equals(2)) {
            Boolean isAccess;
            try {
                isAccess = redisService.sIsMember(ARTICLE_ACCESS + UserUtil.getUserDetailsDTO().getId(), articleId);
            } catch (Exception exception) {
                throw new BizException(ARTICLE_ACCESS_FAIL);
            }
            if (isAccess.equals(false)) {
                throw new BizException(ARTICLE_ACCESS_FAIL);
            }
        }
        updateArticleViewsCount(articleId);
        CompletableFuture<ArticleDTO> asyncArticle = CompletableFuture.supplyAsync(() -> articleMapper.getArticleById(articleId));
        CompletableFuture<ArticleCardDTO> asyncPreArticle = CompletableFuture.supplyAsync(() -> {
            ArticleCardDTO preArticle = articleMapper.getPreArticleById(articleId);
            if (Objects.isNull(preArticle)) {
                preArticle = articleMapper.getLastArticle();
            }
            return preArticle;
        });
        CompletableFuture<ArticleCardDTO> asyncNextArticle = CompletableFuture.supplyAsync(() -> {
            ArticleCardDTO nextArticle = articleMapper.getNextArticleById(articleId);
            if (Objects.isNull(nextArticle)) {
                nextArticle = articleMapper.getFirstArticle();
            }
            return nextArticle;
        });
        ArticleDTO article = asyncArticle.get();
        if (Objects.isNull(article)) {
            return null;
        }
        Double score = redisService.zScore(ARTICLE_VIEWS_COUNT, articleId);
        if (Objects.nonNull(score)) {
            article.setViewCount(score.intValue());
        }
        article.setPreArticleCard(asyncPreArticle.get());
        article.setNextArticleCard(asyncNextArticle.get());
        return article;
    }

    @Override
    public void accessArticle(ArticlePasswordVO articlePasswordVO) {
        Article article = articleMapper.selectOne(new LambdaQueryWrapper<Article>().eq(Article::getId, articlePasswordVO.getArticleId()));
        if (Objects.isNull(article)) {
            throw new BizException("文章不存在");
        }
        if (article.getPassword().equals(articlePasswordVO.getArticlePassword())) {
            redisService.sAdd(ARTICLE_ACCESS + UserUtil.getUserDetailsDTO().getId(), articlePasswordVO.getArticleId());
        } else {
            throw new BizException("密码错误");
        }
    }

    @SneakyThrows
    @Override
    public PageResultDTO<ArticleCardDTO> listArticlesByTagId(Integer tagId) {
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getTagId, tagId);
        CompletableFuture<Integer> asyncCount = CompletableFuture.supplyAsync(() -> Math.toIntExact(articleTagMapper.selectCount(queryWrapper)));
        List<ArticleCardDTO> articles = articleMapper.listArticlesByTagId(PageUtil.getLimitCurrent(), PageUtil.getSize(), tagId);
        return new PageResultDTO<>(articles, asyncCount.get());
    }

    @SneakyThrows
    @Override
    public PageResultDTO<ArchiveDTO> listArchives() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>().eq(Article::getIsDelete, 0).eq(Article::getStatus, 1);
        CompletableFuture<Integer> asyncCount = CompletableFuture.supplyAsync(() -> Math.toIntExact(articleMapper.selectCount(queryWrapper)));
        List<ArticleCardDTO> articles = articleMapper.listArchives(PageUtil.getLimitCurrent(), PageUtil.getSize());
        HashMap<String, List<ArticleCardDTO>> map = new HashMap<>();
        for (ArticleCardDTO article : articles) {
            LocalDateTime createTime = article.getCreateTime();
            int month = createTime.getMonth().getValue();
            int year = createTime.getYear();
            String key = year + "-" + month;
            if (Objects.isNull(map.get(key))) {
                List<ArticleCardDTO> articleCardDTOS = new ArrayList<>();
                articleCardDTOS.add(article);
                map.put(key, articleCardDTOS);
            } else {
                map.get(key).add(article);
            }
        }
        List<ArchiveDTO> archiveDTOs = new ArrayList<>();
        map.forEach((key, value) -> archiveDTOs.add(ArchiveDTO.builder().Time(key).articles(value).build()));
        archiveDTOs.sort((o1, o2) -> {
            String[] o1s = o1.getTime().split("-");
            String[] o2s = o2.getTime().split("-");
            int o1Year = Integer.parseInt(o1s[0]);
            int o1Month = Integer.parseInt(o1s[1]);
            int o2Year = Integer.parseInt(o2s[0]);
            int o2Month = Integer.parseInt(o2s[1]);
            if (o1Year > o2Year) {
                return -1;
            } else if (o1Year < o2Year) {
                return 1;
            } else {
                return Integer.compare(o2Month, o1Month);
            }
        });
        return new PageResultDTO<>(archiveDTOs, asyncCount.get());
    }

    @SneakyThrows
    @Override
    public PageResultDTO<ArticleAdminDTO> listArticlesAdmin(ConditionVO conditionVO) {
        CompletableFuture<Integer> asyncCount = CompletableFuture.supplyAsync(() -> articleMapper.countArticleAdmins(conditionVO));
        List<ArticleAdminDTO> articleAdminDTOs = articleMapper.listArticlesAdmin(PageUtil.getLimitCurrent(), PageUtil.getSize(), conditionVO);
        Map<Object, Double> viewsCountMap = redisService.zAllScore(ARTICLE_VIEWS_COUNT);
        articleAdminDTOs.forEach(item -> {
            Double viewsCount = viewsCountMap.get(item.getId());
            if (Objects.nonNull(viewsCount)) {
                item.setViewsCount(viewsCount.intValue());
            }
        });
        return new PageResultDTO<>(articleAdminDTOs, asyncCount.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateArticle(ArticleVO articleVO) {
        Category category = saveArticleCategory(articleVO);
        Article article = BeanCopyUtil.copyObject(articleVO, Article.class);
        if (Objects.nonNull(category)) {
            article.setCategoryId(category.getId());
        }
        article.setUserId(UserUtil.getUserDetailsDTO().getUserInfoId());
        this.saveOrUpdate(article);
        saveArticleTag(articleVO, article.getId());
        if (article.getStatus().equals(1)) {
            rabbitTemplate.convertAndSend(SUBSCRIBE_EXCHANGE, "*", new Message(JSON.toJSONBytes(article.getId()), new MessageProperties()));
        }
    }

    @Override
    public void updateArticleTopAndFeatured(ArticleTopFeaturedVO articleTopFeaturedVO) {
        Article article = Article.builder()
                .id(articleTopFeaturedVO.getId())
                .isTop(articleTopFeaturedVO.getIsTop())
                .isFeatured(articleTopFeaturedVO.getIsFeatured())
                .build();
        articleMapper.updateById(article);
    }

    @Override
    public void updateArticleDelete(DeleteVO deleteVO) {
        List<Article> articles = deleteVO.getIds().stream()
                .map(id -> Article.builder()
                        .id(id)
                        .isDelete(deleteVO.getIsDelete())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(articles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticles(List<Integer> articleIds) {
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getArticleId, articleIds));
        articleMapper.deleteBatchIds(articleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleAdminViewDTO getArticleByIdAdmin(Integer articleId) {
        Article article = articleMapper.selectById(articleId);
        Category category = categoryMapper.selectById(article.getCategoryId());
        String categoryName = null;
        if (Objects.nonNull(category)) {
            categoryName = category.getCategoryName();
        }
        List<String> tagNames = tagMapper.listTagNamesByArticleId(articleId);
        ArticleAdminViewDTO articleAdminViewDTO = BeanCopyUtil.copyObject(article, ArticleAdminViewDTO.class);
        articleAdminViewDTO.setCategoryName(categoryName);
        articleAdminViewDTO.setTagNames(tagNames);
        return articleAdminViewDTO;
    }

    @Override
    public List<String> exportArticles(List<Integer> articleIds) {
        List<Article> articles = articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getArticleTitle, Article::getArticleContent)
                .in(Article::getId, articleIds));
        List<String> urls = new ArrayList<>();
        for (Article article : articles) {
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(article.getArticleContent().getBytes())) {
                String url = uploadStrategyContext.executeUploadStrategy(article.getArticleTitle() + FileExtEnum.MD.getExtName(), inputStream, FilePathEnum.MD.getPath());
                urls.add(url);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BizException("导出文章失败");
            }
        }
        return urls;
    }

    @Override
    public List<ArticleSearch> listArticlesBySearch(ConditionVO condition) {
        return searchStrategyContext.executeSearchStrategy(condition.getKeywords());
    }

    public void updateArticleViewsCount(Integer articleId) {
        redisService.zIncr(ARTICLE_VIEWS_COUNT, articleId, 1D);
    }

    private Category saveArticleCategory(ArticleVO articleVO) {
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .eq(Category::getCategoryName, articleVO.getCategoryName()));
        if (Objects.isNull(category) && !Objects.equals(articleVO.getStatus(), DRAFT.getStatus())) {
            category = Category.builder()
                    .categoryName(articleVO.getCategoryName())
                    .build();
            categoryMapper.insert(category);
        }
        return category;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveArticleTag(ArticleVO articleVO, Integer articleId) {
        if (Objects.nonNull(articleVO.getId())) {
            articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>()
                    .eq(ArticleTag::getArticleId, articleVO.getId()));
        }
        List<String> tagNames = articleVO.getTagNames();
        if (CollectionUtils.isNotEmpty(tagNames)) {
            List<Tag> existTags = tagService.list(new LambdaQueryWrapper<Tag>()
                    .in(Tag::getTagName, tagNames));
            List<String> existTagNames = existTags.stream()
                    .map(Tag::getTagName)
                    .toList();
            List<Integer> existTagIds = existTags.stream()
                    .map(Tag::getId)
                    .collect(Collectors.toList());
            tagNames.removeAll(existTagNames);
            if (CollectionUtils.isNotEmpty(tagNames)) {
                List<Tag> tags = tagNames.stream().map(item -> Tag.builder()
                                .tagName(item)
                                .build())
                        .collect(Collectors.toList());
                tagService.saveBatch(tags);
                List<Integer> tagIds = tags.stream()
                        .map(Tag::getId)
                        .toList();
                existTagIds.addAll(tagIds);
            }
            List<ArticleTag> articleTags = existTagIds.stream().map(item -> ArticleTag.builder()
                            .articleId(articleId)
                            .tagId(item)
                            .build())
                    .collect(Collectors.toList());
            articleTagService.saveBatch(articleTags);
        }
    }


}
