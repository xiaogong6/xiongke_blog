package com.blog.service;


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
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.modle.bo.ArticleBO;
import com.core.modle.entity.Article;
import com.core.modle.es.ArticleSearch;

import java.util.List;


/**
 * @author xiongke
 */
public interface ArticleService extends IService<Article> {

    TopAndFeaturedArticlesDTO listTopAndFeaturedArticles();

    PageResultDTO<ArticleCardDTO> listArticles();

    PageResultDTO<ArticleCardDTO> listArticlesByCategoryId(Integer categoryId);

    ArticleBO getArticleById(Integer articleId);

    void accessArticle(ArticlePasswordVO articlePasswordVO);

    PageResultDTO<ArticleCardDTO> listArticlesByTagId(Integer tagId);

    PageResultDTO<ArchiveDTO> listArchives();

    PageResultDTO<ArticleAdminDTO> listArticlesAdmin(ConditionVO conditionVO);

    void saveOrUpdateArticle(ArticleVO articleVO);

    void updateArticleTopAndFeatured(ArticleTopFeaturedVO articleTopFeaturedVO);

    void updateArticleDelete(DeleteVO deleteVO);

    void deleteArticles(List<Integer> articleIds);

    ArticleAdminViewDTO getArticleByIdAdmin(Integer articleId);

    List<String> exportArticles(List<Integer> articleIdList);

    List<ArticleSearch> listArticlesBySearch(ConditionVO condition);

}
