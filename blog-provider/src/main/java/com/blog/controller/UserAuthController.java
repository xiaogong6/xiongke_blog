package com.blog.controller;


import com.api.dto.base.PageResultDTO;
import com.api.dto.user.UserAdminDTO;
import com.api.dto.user.UserAreaDTO;
import com.api.dto.user.UserInfoDTO;
import com.api.dto.user.UserLogoutStatusDTO;
import com.api.feign.UserAuthClient;
import com.api.vo.base.ResultVO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.PasswordVO;
import com.api.vo.other.QQLoginVO;
import com.api.vo.user.UserAuthVO;
import com.api.vo.user.UserVO;
import com.blog.convert.CommonConvert;
import com.blog.modle.bo.UserAuthBO;
import com.blog.service.UserAuthService;
import com.core.annotation.AccessLimit;
import com.core.annotation.OptLog;
import com.api.response.ServerResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.api.constant.OptTypeConstant.UPDATE;


/**
 * @author xiongke
 */
@Api(tags = "用户账号模块")
@RestController
public class UserAuthController implements UserAuthClient {

    @Autowired
    private UserAuthService userAuthService;

    @AccessLimit(seconds = 60, maxCount = 1)
    @ApiOperation(value = "发送邮箱验证码")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String")
    @GetMapping("/users/code")
    public ResultVO<?> sendCode(String username) {
        userAuthService.sendCode(username);
        return ResultVO.ok();
    }

    @ApiOperation(value = "获取用户区域分布")
    @GetMapping("/admin/users/area")
    public ResultVO<List<UserAreaDTO>> listUserAreas(ConditionVO conditionVO) {
        return ResultVO.ok(userAuthService.listUserAreas(conditionVO));
    }

    @ApiOperation(value = "查询后台用户列表")
    @GetMapping("/admin/users")
    public ResultVO<PageResultDTO<UserAdminDTO>> listUsers(ConditionVO conditionVO) {
        return ResultVO.ok(userAuthService.listUsers(conditionVO));
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/users/register")
    public ResultVO<?> register(@Valid @RequestBody UserVO userVO) {
        userAuthService.register(userVO);
        return ResultVO.ok();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改密码")
    @PutMapping("/users/password")
    public ResultVO<?> updatePassword(@Valid @RequestBody UserVO user) {
        userAuthService.updatePassword(user);
        return ResultVO.ok();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改管理员密码")
    @PutMapping("/admin/users/password")
    public ResultVO<?> updateAdminPassword(@Valid @RequestBody PasswordVO passwordVO) {
        userAuthService.updateAdminPassword(passwordVO);
        return ResultVO.ok();
    }

    @ApiOperation("用户登出")
    @PostMapping("/users/logout")
    public ResultVO<UserLogoutStatusDTO> logout() {
        return ResultVO.ok(userAuthService.logout());
    }

    @ApiOperation(value = "qq登录")
    @PostMapping("/users/oauth/qq")
    public ResultVO<UserInfoDTO> qqLogin(@Valid @RequestBody QQLoginVO qqLoginVO) {
        return ResultVO.ok(userAuthService.qqLogin(qqLoginVO));
    }

    @Override
    public ServerResponseEntity<?> updateById(UserAuthVO userAuthVO) {
        UserAuthBO userAuthBO = CommonConvert.INSTANCE.converToUserAuthBO(userAuthVO);
        userAuthService.updateById(userAuthBO);
        return ServerResponseEntity.success(Boolean.TRUE);
    }
}
