package com.blog.service;


import com.api.dto.other.LabelOptionDTO;
import com.api.dto.other.MenuDTO;
import com.api.dto.user.UserMenuDTO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.IsHiddenVO;
import com.api.vo.other.MenuVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<MenuDTO> listMenus(ConditionVO conditionVO);

    void saveOrUpdateMenu(MenuVO menuVO);

    void updateMenuIsHidden(IsHiddenVO isHiddenVO);

    void deleteMenu(Integer menuId);

    List<LabelOptionDTO> listMenuOptions();

    List<UserMenuDTO> listUserMenus();

}
