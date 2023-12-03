package com.blog.controller;


import com.api.dto.base.PageResultDTO;
import com.api.dto.other.RoleDTO;
import com.api.dto.user.UserRoleDTO;
import com.api.vo.base.ResultVO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.RoleVO;
import com.blog.service.RoleService;
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
@Api(tags = "角色模块")
@RestController
public class RoleController {

    @Resource
    private RoleService roleService;

    @ApiOperation(value = "查询用户角色选项")
    @GetMapping("/admin/users/role")
    public ResultVO<List<UserRoleDTO>> listUserRoles() {
        return ResultVO.ok(roleService.listUserRoles());
    }


    @ApiOperation(value = "查询角色列表")
    @GetMapping("/admin/roles")
    public ResultVO<PageResultDTO<RoleDTO>> listRoles(ConditionVO conditionVO) {
        return ResultVO.ok(roleService.listRoles(conditionVO));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或更新角色")
    @PostMapping("/admin/role")
    public ResultVO<?> saveOrUpdateRole(@RequestBody @Valid RoleVO roleVO) {
        roleService.saveOrUpdateRole(roleVO);
        return ResultVO.ok();
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/admin/roles")
    public ResultVO<?> deleteRoles(@RequestBody List<Integer> roleIdList) {
        roleService.deleteRoles(roleIdList);
        return ResultVO.ok();
    }
}
