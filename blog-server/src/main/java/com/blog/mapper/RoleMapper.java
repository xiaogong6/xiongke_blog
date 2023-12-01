package com.blog.mapper;

import com.api.dto.other.RoleDTO;
import com.api.dto.resource.ResourceRoleDTO;
import com.api.vo.other.ConditionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.modle.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<ResourceRoleDTO> listResourceRoles();

    List<String> listRolesByUserInfoId(@Param("userInfoId") Integer userInfoId);

    List<RoleDTO> listRoles(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);

}
