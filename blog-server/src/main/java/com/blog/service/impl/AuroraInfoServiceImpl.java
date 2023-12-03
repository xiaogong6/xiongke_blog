package com.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.api.dto.article.ArticleRankDTO;
import com.api.dto.article.ArticleStatisticsDTO;
import com.api.dto.aurora.AuroraAdminInfoDTO;
import com.api.dto.aurora.AuroraHomeInfoDTO;
import com.api.dto.category.CategoryDTO;
import com.api.dto.other.AboutDTO;
import com.api.dto.other.UniqueViewDTO;
import com.api.dto.other.WebsiteConfigDTO;
import com.api.dto.tag.TagDTO;
import com.api.vo.other.AboutVO;
import com.api.vo.other.WebsiteConfigVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.blog.service.AuroraInfoService;
import com.blog.service.RedisService;
import com.blog.service.UniqueViewService;
import com.core.mapper.*;
import com.core.modle.entity.About;
import com.core.modle.entity.Article;
import com.core.modle.entity.Comment;
import com.core.modle.entity.WebsiteConfig;
import com.core.util.BeanCopyUtil;
import com.core.util.IpUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.api.constant.CommonConstant.*;
import static com.api.constant.RedisConstant.*;


/**
 * @author xiongke
 */
@Service
public class AuroraInfoServiceImpl implements AuroraInfoService {

    @Resource
    private WebsiteConfigMapper websiteConfigMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private TalkMapper talkMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private AboutMapper aboutMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private UniqueViewService uniqueViewService;

    @Resource
    private HttpServletRequest request;

    @Override
    public void report() {
        String ipAddress = IpUtil.getIpAddress(request);
        UserAgent userAgent = IpUtil.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        if (!redisService.sIsMember(UNIQUE_VISITOR, md5)) {
            String ipSource = IpUtil.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                String ipProvince = IpUtil.getIpProvince(ipSource);
                redisService.hIncr(VISITOR_AREA, ipProvince, 1L);
            } else {
                redisService.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }
            redisService.incr(BLOG_VIEWS_COUNT, 1);
            redisService.sAdd(UNIQUE_VISITOR, md5);
        }
    }

    @SneakyThrows
    @Override
    public AuroraHomeInfoDTO getAuroraHomeInfo() {
        CompletableFuture<Integer> asyncArticleCount = CompletableFuture.supplyAsync(() -> Math.toIntExact(articleMapper.selectCount(new LambdaQueryWrapper<Article>().eq(Article::getIsDelete, FALSE))));
        CompletableFuture<Integer> asyncCategoryCount = CompletableFuture.supplyAsync(() -> Math.toIntExact(categoryMapper.selectCount(null)));
        CompletableFuture<Integer> asyncTagCount = CompletableFuture.supplyAsync(() -> Math.toIntExact(tagMapper.selectCount(null)));
        CompletableFuture<Integer> asyncTalkCount = CompletableFuture.supplyAsync(() -> Math.toIntExact(talkMapper.selectCount(null)));
        CompletableFuture<WebsiteConfigDTO> asyncWebsiteConfig = CompletableFuture.supplyAsync(this::getWebsiteConfig);
        CompletableFuture<Integer> asyncViewCount = CompletableFuture.supplyAsync(() -> {
            Object count = redisService.get(BLOG_VIEWS_COUNT);
            return Integer.parseInt(Optional.ofNullable(count).orElse(0).toString());
        });
        return AuroraHomeInfoDTO.builder()
                .articleCount(asyncArticleCount.get())
                .categoryCount(asyncCategoryCount.get())
                .tagCount(asyncTagCount.get())
                .talkCount(asyncTalkCount.get())
                .websiteConfigDTO(asyncWebsiteConfig.get())
                .viewCount(asyncViewCount.get()).build();
    }

    @Override
    public AuroraAdminInfoDTO getAuroraAdminInfo() {
        Object count = redisService.get(BLOG_VIEWS_COUNT);
        Integer viewsCount = Integer.parseInt(Optional.ofNullable(count).orElse(0).toString());
        Integer messageCount = Math.toIntExact(commentMapper.selectCount(new LambdaQueryWrapper<Comment>().eq(Comment::getType, 2)));
        Integer userCount = Math.toIntExact(userInfoMapper.selectCount(null));
        Integer articleCount = Math.toIntExact(articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, FALSE)));
        List<UniqueViewDTO> uniqueViews = uniqueViewService.listUniqueViews();
        List<ArticleStatisticsDTO> articleStatisticsDTOs = articleMapper.listArticleStatistics();
        List<CategoryDTO> categoryDTOs = categoryMapper.listCategories();
        List<TagDTO> tagDTOs = BeanCopyUtil.copyList(tagMapper.selectList(null), TagDTO.class);
        Map<Object, Double> articleMap = redisService.zReverseRangeWithScore(ARTICLE_VIEWS_COUNT, 0, 4);
        AuroraAdminInfoDTO auroraAdminInfoDTO = AuroraAdminInfoDTO.builder()
                .articleStatisticsDTOs(articleStatisticsDTOs)
                .tagDTOs(tagDTOs)
                .viewsCount(viewsCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .categoryDTOs(categoryDTOs)
                .uniqueViewDTOs(uniqueViews)
                .build();
        if (CollectionUtils.isNotEmpty(articleMap)) {
            List<ArticleRankDTO> articleRankDTOList = listArticleRank(articleMap);
            auroraAdminInfoDTO.setArticleRankDTOs(articleRankDTOList);
        }
        return auroraAdminInfoDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO) {
        WebsiteConfig websiteConfig = WebsiteConfig.builder()
                .id(DEFAULT_CONFIG_ID)
                .config(JSON.toJSONString(websiteConfigVO))
                .build();
        websiteConfigMapper.updateById(websiteConfig);
        redisService.del(WEBSITE_CONFIG);
    }

    @Override
    public WebsiteConfigDTO getWebsiteConfig() {
        WebsiteConfigDTO websiteConfigDTO;
        Object websiteConfig = redisService.get(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigDTO = JSON.parseObject(websiteConfig.toString(), WebsiteConfigDTO.class);
        } else {
            String config = websiteConfigMapper.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigDTO = JSON.parseObject(config, WebsiteConfigDTO.class);
            redisService.set(WEBSITE_CONFIG, config);
        }
        return websiteConfigDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAbout(AboutVO aboutVO) {
        About about = About.builder()
                .id(DEFAULT_ABOUT_ID)
                .content(JSON.toJSONString(aboutVO))
                .build();
        aboutMapper.updateById(about);
        redisService.del(ABOUT);
    }

    @Override
    public AboutDTO getAbout() {
        AboutDTO aboutDTO;
        Object about = redisService.get(ABOUT);
        if (Objects.nonNull(about)) {
            aboutDTO = JSON.parseObject(about.toString(), AboutDTO.class);
        } else {
            String content = aboutMapper.selectById(DEFAULT_ABOUT_ID).getContent();
            aboutDTO = JSON.parseObject(content, AboutDTO.class);
            redisService.set(ABOUT, content);
        }
        return aboutDTO;
    }

    private List<ArticleRankDTO> listArticleRank(Map<Object, Double> articleMap) {
        List<Integer> articleIds = new ArrayList<>(articleMap.size());
        articleMap.forEach((key, value) -> articleIds.add((Integer) key));
        return articleMapper.selectList(new LambdaQueryWrapper<Article>()
                        .select(Article::getId, Article::getArticleTitle)
                        .in(Article::getId, articleIds))
                .stream().map(article -> ArticleRankDTO.builder()
                        .articleTitle(article.getArticleTitle())
                        .viewsCount(articleMap.get(article.getId()).intValue())
                        .build())
                .sorted(Comparator.comparingInt(ArticleRankDTO::getViewsCount).reversed())
                .collect(Collectors.toList());
    }

}
