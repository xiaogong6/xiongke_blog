package com.blog.controller;


import com.api.dto.article.ArchiveDTO;
import com.api.dto.article.ArticleAdminDTO;
import com.api.dto.article.ArticleAdminViewDTO;
import com.api.dto.article.ArticleCardDTO;
import com.api.dto.base.PageResultDTO;
import com.api.dto.other.ArticleSearchDTO;
import com.api.dto.other.TopAndFeaturedArticlesDTO;
import com.api.enums.FilePathEnum;
import com.api.feign.ArticleClient;
import com.api.response.ServerResponseEntity;
import com.api.vo.article.ArticlePasswordVO;
import com.api.vo.article.ArticleTopFeaturedVO;
import com.api.vo.article.ArticleVO;
import com.api.vo.base.ResultVO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.DeleteVO;
import com.blog.convert.CommonConvert;
import com.blog.modle.bo.ArticleBO;
import com.blog.service.ArticleService;
import com.blog.strategy.context.ArticleImportStrategyContext;
import com.blog.strategy.context.UploadStrategyContext;
import com.core.annotation.OptLog;
import com.core.modle.es.ArticleSearch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.api.constant.OptTypeConstant.*;

@Api(tags = "文章模块")
@RestController
public class ArticleController implements ArticleClient {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private ArticleImportStrategyContext articleImportStrategyContext;

    @ApiOperation("获取置顶和推荐文章")
    @GetMapping("/articles/topAndFeatured")
    public ResultVO<TopAndFeaturedArticlesDTO> listTopAndFeaturedArticles() {
        return ResultVO.ok(articleService.listTopAndFeaturedArticles());
    }

    @ApiOperation("获取所有文章")
    @GetMapping("/articles/all")
    public ResultVO<PageResultDTO<ArticleCardDTO>> listArticles() {
        return ResultVO.ok(articleService.listArticles());
    }

    @ApiOperation("根据分类id获取文章")
    @GetMapping("/articles/categoryId")
    public ResultVO<PageResultDTO<ArticleCardDTO>> getArticlesByCategoryId(@RequestParam Integer categoryId) {
        return ResultVO.ok(articleService.listArticlesByCategoryId(categoryId));
    }

    @ApiOperation("根据id获取文章")
    @GetMapping("/articles/{articleId}")
    public ServerResponseEntity<ArticleVO> getArticleById(@PathVariable("articleId") Integer articleId) {
        ArticleBO resBO = articleService.getArticleById(articleId);
        ArticleVO resVO = CommonConvert.INSTANCE.converToArticleVO(resBO);
        return ServerResponseEntity.success(resVO);
    }

    @ApiOperation("校验文章访问密码")
    @PostMapping("/articles/access")
    public ResultVO<?> accessArticle(@Valid @RequestBody ArticlePasswordVO articlePasswordVO) {
        articleService.accessArticle(articlePasswordVO);
        return ResultVO.ok();
    }

    @ApiOperation("根据标签id获取文章")
    @GetMapping("/articles/tagId")
    public ResultVO<PageResultDTO<ArticleCardDTO>> listArticlesByTagId(@RequestParam Integer tagId) {
        return ResultVO.ok(articleService.listArticlesByTagId(tagId));
    }

    @ApiOperation("获取所有文章归档")
    @GetMapping("/archives/all")
    public ResultVO<PageResultDTO<ArchiveDTO>> listArchives() {
        return ResultVO.ok(articleService.listArchives());
    }

    @ApiOperation("获取后台文章")
    @GetMapping("/admin/articles")
    public ResultVO<PageResultDTO<ArticleAdminDTO>> listArticlesAdmin(ConditionVO conditionVO) {
        return ResultVO.ok(articleService.listArticlesAdmin(conditionVO));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation("保存和修改文章")
    @PostMapping("/admin/articles")
    public ResultVO<?> saveOrUpdateArticle(@Valid @RequestBody ArticleVO articleVO) {
        articleService.saveOrUpdateArticle(articleVO);
        return ResultVO.ok();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation("修改文章是否置顶和推荐")
    @PutMapping("/admin/articles/topAndFeatured")
    public ResultVO<?> updateArticleTopAndFeatured(@Valid @RequestBody ArticleTopFeaturedVO articleTopFeaturedVO) {
        articleService.updateArticleTopAndFeatured(articleTopFeaturedVO);
        return ResultVO.ok();
    }

    @ApiOperation("删除或者恢复文章")
    @PutMapping("/admin/articles")
    public ResultVO<?> updateArticleDelete(@Valid @RequestBody DeleteVO deleteVO) {
        articleService.updateArticleDelete(deleteVO);
        return ResultVO.ok();
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "物理删除文章")
    @DeleteMapping("/admin/articles/delete")
    public ResultVO<?> deleteArticles(@RequestBody List<Integer> articleIds) {
        articleService.deleteArticles(articleIds);
        return ResultVO.ok();
    }

    @OptLog(optType = UPLOAD)
    @ApiOperation("上传文章图片")
    @ApiImplicitParam(name = "file", value = "文章图片", required = true, dataType = "MultipartFile")
    @PostMapping("/admin/articles/images")
    public ResultVO<String> saveArticleImages(MultipartFile file) {
        return ResultVO.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.ARTICLE.getPath()));
    }

    @ApiOperation("根据id查看后台文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @GetMapping("/admin/articles/{articleId}")
    public ResultVO<ArticleAdminViewDTO> getArticleBackById(@PathVariable("articleId") Integer articleId) {
        return ResultVO.ok(articleService.getArticleByIdAdmin(articleId));
    }

    @OptLog(optType = UPLOAD)
    @ApiOperation(value = "导入文章")
    @PostMapping("/admin/articles/import")
    public ResultVO<?> importArticles(MultipartFile file, @RequestParam(required = false) String type) {
        articleImportStrategyContext.importArticles(file, type);
        return ResultVO.ok();
    }

    @OptLog(optType = EXPORT)
    @ApiOperation(value = "导出文章")
    @ApiImplicitParam(name = "articleIds", value = "文章id", required = true, dataType = "List<Integer>")
    @PostMapping("/admin/articles/export")
    public ResultVO<List<String>> exportArticles(@RequestBody List<Integer> articleIds) {
        return ResultVO.ok(articleService.exportArticles(articleIds));
    }

    @ApiOperation(value = "搜索文章")
    @GetMapping("/articles/search")
    public ResultVO<List<ArticleSearchDTO>> listArticlesBySearch(ConditionVO condition) {
        List<ArticleSearch> articleSearchList = articleService.listArticlesBySearch(condition);
        List<ArticleSearchDTO> result = CommonConvert.INSTANCE.converToArticleSearchDTOList(articleSearchList);
        return ResultVO.ok(result);
    }

}
