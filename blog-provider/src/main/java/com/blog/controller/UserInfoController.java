package com.blog.controller;

import com.api.dto.base.PageResultDTO;
import com.api.dto.user.UserInfoDTO;
import com.api.dto.user.UserOnlineDTO;
import com.api.feign.UserInfoClient;
import com.api.vo.base.ResultVO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.EmailVO;
import com.api.vo.other.SubscribeVO;
import com.api.vo.user.UserDisableVO;
import com.api.vo.user.UserInfoVO;
import com.api.vo.user.UserRoleVO;
import com.blog.convert.CommonConvert;
import com.blog.modle.bo.UserInfoBO;
import com.blog.service.UserInfoService;
import com.core.annotation.OptLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.api.constant.OptTypeConstant.DELETE;
import static com.api.constant.OptTypeConstant.UPDATE;

/**
 * @author xiongke
 * @Program: xiongke_blog
 * @Description:
 * @Create: 2023-12-01
 */
@Api(tags = "用户信息模块")
@RestController
public class UserInfoController implements UserInfoClient {

    @Resource
    private UserInfoService userInfoService;

    @Override
    public ResultVO<List<UserInfoVO>> getUserInfoSubscribeList() {
        List<UserInfoBO> resBOList = userInfoService.getUserInfoSubscribeList();
        List<UserInfoVO> result = CommonConvert.INSTANCE.converToUserInfoVOList(resBOList);
        return ResultVO.ok(result);
    }

    @OptLog(optType = UPDATE)
    @ApiOperation("更新用户信息")
    @PutMapping("/users/info")
    public ResultVO<?> updateUserInfo(@Valid @RequestBody UserInfoVO userInfoVO) {
        userInfoService.updateUserInfo(userInfoVO);
        return ResultVO.ok();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation("更新用户头像")
    @ApiImplicitParam(name = "file", value = "用户头像", required = true, dataType = "MultipartFile")
    @PostMapping("/users/avatar")
    public ResultVO<String> updateUserAvatar(MultipartFile file) {
        return ResultVO.ok(userInfoService.updateUserAvatar(file));
    }

    @OptLog(optType = UPDATE)
    @ApiOperation("绑定用户邮箱")
    @PutMapping("/users/email")
    public ResultVO<?> saveUserEmail(@Valid @RequestBody EmailVO emailVO) {
        userInfoService.saveUserEmail(emailVO);
        return ResultVO.ok();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation("修改用户的订阅状态")
    @PutMapping("/users/subscribe")
    public ResultVO<?> updateUserSubscribe(@RequestBody SubscribeVO subscribeVO) {
        userInfoService.updateUserSubscribe(subscribeVO);
        return ResultVO.ok();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改用户角色")
    @PutMapping("/admin/users/role")
    public ResultVO<?> updateUserRole(@Valid @RequestBody UserRoleVO userRoleVO) {
        userInfoService.updateUserRole(userRoleVO);
        return ResultVO.ok();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改用户禁用状态")
    @PutMapping("/admin/users/disable")
    public ResultVO<?> updateUserDisable(@Valid @RequestBody UserDisableVO userDisableVO) {
        userInfoService.updateUserDisable(userDisableVO);
        return ResultVO.ok();
    }

    @ApiOperation(value = "查看在线用户")
    @GetMapping("/admin/users/online")
    public ResultVO<PageResultDTO<UserOnlineDTO>> listOnlineUsers(ConditionVO conditionVO) {
        return ResultVO.ok(userInfoService.listOnlineUsers(conditionVO));
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "下线用户")
    @DeleteMapping("/admin/users/{userInfoId}/online")
    public ResultVO<?> removeOnlineUser(@PathVariable("userInfoId") Integer userInfoId) {
        userInfoService.removeOnlineUser(userInfoId);
        return ResultVO.ok();
    }

    @ApiOperation("根据id获取用户信息")
    @GetMapping("/users/info/{userInfoId}")
    public ResultVO<UserInfoDTO> getUserInfoById(@PathVariable("userInfoId") Integer userInfoId) {
        return ResultVO.ok(userInfoService.getUserInfoById(userInfoId));
    }

}
