package com.blog.service.impl;

import com.api.dto.article.ArchiveDTO;
import com.api.dto.article.ArticleAdminDTO;
import com.api.dto.article.ArticleAdminViewDTO;
import com.api.dto.article.ArticleCardDTO;
import com.api.dto.base.PageResultDTO;
import com.api.dto.other.TopAndFeaturedArticlesDTO;
import com.api.vo.article.ArticlePasswordVO;
import com.api.vo.article.ArticleTopFeaturedVO;
import com.api.vo.article.ArticleVO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.DeleteVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.convert.ServiceConvert;
import com.blog.modle.bo.ArticleBO;
import com.blog.repo.ArticleRepo;
import com.blog.service.ArticleService;
import com.core.modle.entity.Article;
import com.core.modle.es.ArticleSearch;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleRepo articleRepo;

    @Override
    public TopAndFeaturedArticlesDTO listTopAndFeaturedArticles() {
        return null;
    }

    @Override
    public PageResultDTO<ArticleCardDTO> listArticles() {
        return null;
    }

    @Override
    public PageResultDTO<ArticleCardDTO> listArticlesByCategoryId(Integer categoryId) {
        return null;
    }

    @Override
    public ArticleBO getArticleById(Integer articleId) {
        Article article = articleRepo.getArticleById(articleId);
        return ServiceConvert.INSTANCE.convertArticleBO(article);
    }

    @Override
    public void accessArticle(ArticlePasswordVO articlePasswordVO) {

    }

    @Override
    public PageResultDTO<ArticleCardDTO> listArticlesByTagId(Integer tagId) {
        return null;
    }

    @Override
    public PageResultDTO<ArchiveDTO> listArchives() {
        return null;
    }

    @Override
    public PageResultDTO<ArticleAdminDTO> listArticlesAdmin(ConditionVO conditionVO) {
        return null;
    }

    @Override
    public void saveOrUpdateArticle(ArticleVO articleVO) {

    }

    @Override
    public void updateArticleTopAndFeatured(ArticleTopFeaturedVO articleTopFeaturedVO) {

    }

    @Override
    public void updateArticleDelete(DeleteVO deleteVO) {

    }

    @Override
    public void deleteArticles(List<Integer> articleIds) {

    }

    @Override
    public ArticleAdminViewDTO getArticleByIdAdmin(Integer articleId) {
        return null;
    }

    @Override
    public List<String> exportArticles(List<Integer> articleIdList) {
        return null;
    }

    @Override
    public List<ArticleSearch> listArticlesBySearch(ConditionVO condition) {
        return null;
    }

    @Override
    public boolean saveBatch(Collection<Article> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<Article> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<Article> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(Article entity) {
        return false;
    }

    @Override
    public Article getOne(Wrapper<Article> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<Article> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<Article> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<Article> getBaseMapper() {
        return null;
    }

    @Override
    public Class<Article> getEntityClass() {
        return null;
    }
}
