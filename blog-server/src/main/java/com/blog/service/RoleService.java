package com.blog.service;


import com.api.dto.base.PageResultDTO;
import com.api.dto.other.RoleDTO;
import com.api.dto.user.UserRoleDTO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.RoleVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<UserRoleDTO> listUserRoles();

    PageResultDTO<RoleDTO> listRoles(ConditionVO conditionVO);

    void saveOrUpdateRole(RoleVO roleVO);

    void deleteRoles(List<Integer> ids);

}
