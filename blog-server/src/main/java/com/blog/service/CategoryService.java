package com.blog.service;


import com.api.dto.base.PageResultDTO;
import com.api.dto.category.CategoryAdminDTO;
import com.api.dto.category.CategoryDTO;
import com.api.dto.category.CategoryOptionDTO;
import com.api.vo.other.CategoryVO;
import com.api.vo.other.ConditionVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {

    List<CategoryDTO> listCategories();

    PageResultDTO<CategoryAdminDTO> listCategoriesAdmin(ConditionVO conditionVO);

    List<CategoryOptionDTO> listCategoriesBySearch(ConditionVO conditionVO);

    void deleteCategories(List<Integer> categoryIds);

    void saveOrUpdateCategory(CategoryVO categoryVO);

}
