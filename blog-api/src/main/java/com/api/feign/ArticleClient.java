package com.api.feign;

import com.api.dto.article.*;
import com.api.dto.base.PageResultDTO;
import com.api.dto.other.ArticleSearchDTO;
import com.api.dto.other.TopAndFeaturedArticlesDTO;
import com.api.vo.article.ArticlePasswordVO;
import com.api.vo.article.ArticleTopFeaturedVO;
import com.api.vo.article.ArticleVO;
import com.api.vo.base.ResultVO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.DeleteVO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@FeignClient(name = "blog-provider", contextId = "ArticleClient")
public interface ArticleClient {

    @GetMapping("/articles/{articleId}")
    ResultVO<ArticleDTO> getArticleById(@RequestParam("id") Integer id);

    @GetMapping("/articles/topAndFeatured")
    ResultVO<TopAndFeaturedArticlesDTO> listTopAndFeaturedArticles();

    @GetMapping("/articles/all")
    ResultVO<PageResultDTO<ArticleCardDTO>> listArticles();

    @GetMapping("/articles/categoryId")
    ResultVO<PageResultDTO<ArticleCardDTO>> getArticlesByCategoryId(@RequestParam Integer categoryId);

    @PostMapping("/articles/access")
    ResultVO<?> accessArticle(@Valid @RequestBody ArticlePasswordVO articlePasswordVO);


    @GetMapping("/articles/tagId")
    ResultVO<PageResultDTO<ArticleCardDTO>> listArticlesByTagId(@RequestParam Integer tagId);


    @GetMapping("/archives/all")
    ResultVO<PageResultDTO<ArchiveDTO>> listArchives();

    @GetMapping("/admin/articles")
    ResultVO<PageResultDTO<ArticleAdminDTO>> listArticlesAdmin(ConditionVO conditionVO);


    @PostMapping("/admin/articles")
    ResultVO<?> saveOrUpdateArticle(@Valid @RequestBody ArticleVO articleVO);


    @PutMapping("/admin/articles/topAndFeatured")
    ResultVO<?> updateArticleTopAndFeatured(@Valid @RequestBody ArticleTopFeaturedVO articleTopFeaturedVO);

    @PutMapping("/admin/articles")
    ResultVO<?> updateArticleDelete(@Valid @RequestBody DeleteVO deleteVO);

    @DeleteMapping("/admin/articles/delete")
    ResultVO<?> deleteArticles(@RequestBody List<Integer> articleIds);

    @PostMapping("/admin/articles/images")
    ResultVO<String> saveArticleImages(MultipartFile file);

    @GetMapping("/admin/articles/{articleId}")
    ResultVO<ArticleAdminViewDTO> getArticleBackById(@PathVariable("articleId") Integer articleId);


    @PostMapping("/admin/articles/import")
    ResultVO<?> importArticles(MultipartFile file, @RequestParam(required = false) String type);

    @PostMapping("/admin/articles/export")
    ResultVO<List<String>> exportArticles(@RequestBody List<Integer> articleIds);


    @GetMapping("/articles/search")
    ResultVO<List<ArticleSearchDTO>> listArticlesBySearch(ConditionVO condition);


}
