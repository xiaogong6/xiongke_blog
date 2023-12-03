package com.blog.service.impl;


import com.api.dto.other.LabelOptionDTO;
import com.api.dto.other.MenuDTO;
import com.api.dto.user.UserMenuDTO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.IsHiddenVO;
import com.api.vo.other.MenuVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.service.MenuService;
import com.core.exception.BizException;
import com.core.mapper.MenuMapper;
import com.core.mapper.RoleMenuMapper;
import com.core.modle.entity.Menu;
import com.core.modle.entity.RoleMenu;
import com.core.util.BeanCopyUtil;
import com.core.util.UserUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.api.constant.CommonConstant.COMPONENT;
import static com.api.constant.CommonConstant.TRUE;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<MenuDTO> listMenus(ConditionVO conditionVO) {
        List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Menu::getName, conditionVO.getKeywords()));
        List<Menu> catalogs = listCatalogs(menus);
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menus);
        List<MenuDTO> menuDTOs = catalogs.stream().map(item -> {
            MenuDTO menuDTO = BeanCopyUtil.copyObject(item, MenuDTO.class);
            List<MenuDTO> list = BeanCopyUtil.copyList(childrenMap.get(item.getId()), MenuDTO.class).stream()
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTO.setChildren(list);
            childrenMap.remove(item.getId());
            return menuDTO;
        }).sorted(Comparator.comparing(MenuDTO::getOrderNum)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Menu> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);
            List<MenuDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanCopyUtil.copyObject(item, MenuDTO.class))
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTOs.addAll(childrenDTOList);
        }
        return menuDTOs;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateMenu(MenuVO menuVO) {
        Menu menu = BeanCopyUtil.copyObject(menuVO, Menu.class);
        this.saveOrUpdate(menu);
    }

    @Override
    public void updateMenuIsHidden(IsHiddenVO isHiddenVO) {
        Menu menu = BeanCopyUtil.copyObject(isHiddenVO, Menu.class);
        menuMapper.updateById(menu);
    }

    @Override
    public void deleteMenu(Integer menuId) {
        Integer count = Math.toIntExact(roleMenuMapper.selectCount(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getMenuId, menuId)));
        if (count > 0) {
            throw new BizException("菜单下有角色关联");
        }
        List<Integer> menuIds = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                        .select(Menu::getId)
                        .eq(Menu::getParentId, menuId))
                .stream()
                .map(Menu::getId)
                .collect(Collectors.toList());
        menuIds.add(menuId);
        menuMapper.deleteBatchIds(menuIds);
    }

    @Override
    public List<LabelOptionDTO> listMenuOptions() {
        List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId, Menu::getName, Menu::getParentId, Menu::getOrderNum));
        List<Menu> catalogs = listCatalogs(menus);
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menus);
        return catalogs.stream().map(item -> {
            List<LabelOptionDTO> list = new ArrayList<>();
            List<Menu> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                list = children.stream()
                        .sorted(Comparator.comparing(Menu::getOrderNum))
                        .map(menu -> LabelOptionDTO.builder()
                                .id(menu.getId())
                                .label(menu.getName())
                                .build())
                        .collect(Collectors.toList());
            }
            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserMenuDTO> listUserMenus() {
        List<Menu> menus = menuMapper.listMenusByUserInfoId(UserUtil.getUserDetailsDTO().getUserInfoId());
        List<Menu> catalogs = listCatalogs(menus);
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menus);
        return convertUserMenuList(catalogs, childrenMap);
    }

    private List<Menu> listCatalogs(List<Menu> menus) {
        return menus.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .sorted(Comparator.comparing(Menu::getOrderNum))
                .collect(Collectors.toList());
    }

    private Map<Integer, List<Menu>> getMenuMap(List<Menu> menus) {
        return menus.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Menu::getParentId));
    }

    private List<UserMenuDTO> convertUserMenuList(List<Menu> catalogList, Map<Integer, List<Menu>> childrenMap) {
        return catalogList.stream().map(item -> {
            UserMenuDTO userMenuDTO = new UserMenuDTO();
            List<UserMenuDTO> list = new ArrayList<>();
            List<Menu> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                userMenuDTO = BeanCopyUtil.copyObject(item, UserMenuDTO.class);
                list = children.stream()
                        .sorted(Comparator.comparing(Menu::getOrderNum))
                        .map(menu -> {
                            UserMenuDTO dto = BeanCopyUtil.copyObject(menu, UserMenuDTO.class);
                            dto.setHidden(menu.getIsHidden().equals(TRUE));
                            return dto;
                        })
                        .collect(Collectors.toList());
            } else {
                userMenuDTO.setPath(item.getPath());
                userMenuDTO.setComponent(COMPONENT);
                list.add(UserMenuDTO.builder()
                        .path("")
                        .name(item.getName())
                        .icon(item.getIcon())
                        .component(item.getComponent())
                        .build());
            }
            userMenuDTO.setHidden(item.getIsHidden().equals(TRUE));
            userMenuDTO.setChildren(list);
            return userMenuDTO;
        }).collect(Collectors.toList());
    }

}
