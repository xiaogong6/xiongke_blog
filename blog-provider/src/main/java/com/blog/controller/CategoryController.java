package com.blog.controller;


import com.api.dto.base.PageResultDTO;
import com.api.dto.category.CategoryAdminDTO;
import com.api.dto.category.CategoryDTO;
import com.api.dto.category.CategoryOptionDTO;
import com.api.vo.base.ResultVO;
import com.api.vo.other.CategoryVO;
import com.api.vo.other.ConditionVO;
import com.blog.service.CategoryService;
import com.core.annotation.OptLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.api.constant.OptTypeConstant.DELETE;
import static com.api.constant.OptTypeConstant.SAVE_OR_UPDATE;


/**
 * @author xiongke
 */
@Api(tags = "分类模块")
@RestController
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @ApiOperation("获取所有分类")
    @GetMapping("/categories/all")
    public ResultVO<List<CategoryDTO>> listCategories() {
        return ResultVO.ok(categoryService.listCategories());
    }

    @ApiOperation(value = "查看后台分类列表")
    @GetMapping("/admin/categories")
    public ResultVO<PageResultDTO<CategoryAdminDTO>> listCategoriesAdmin(ConditionVO conditionVO) {
        return ResultVO.ok(categoryService.listCategoriesAdmin(conditionVO));
    }

    @ApiOperation(value = "搜索文章分类")
    @GetMapping("/admin/categories/search")
    public ResultVO<List<CategoryOptionDTO>> listCategoriesAdminBySearch(ConditionVO conditionVO) {
        return ResultVO.ok(categoryService.listCategoriesBySearch(conditionVO));
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "删除分类")
    @DeleteMapping("/admin/categories")
    public ResultVO<?> deleteCategories(@RequestBody List<Integer> categoryIds) {
        categoryService.deleteCategories(categoryIds);
        return ResultVO.ok();
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加或修改分类")
    @PostMapping("/admin/categories")
    public ResultVO<?> saveOrUpdateCategory(@Valid @RequestBody CategoryVO categoryVO) {
        categoryService.saveOrUpdateCategory(categoryVO);
        return ResultVO.ok();
    }


}
