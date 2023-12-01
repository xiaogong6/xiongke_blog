package com.blog.controller;

import com.api.feign.UserInfoClient;
import com.api.response.ServerResponseEntity;
import com.api.vo.user.UserInfoVO;
import com.blog.convert.CommonConvert;
import com.blog.modle.bo.UserInfoBO;
import com.blog.service.UserInfoService;
import io.swagger.annotations.Api;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ServerResponseEntity<List<UserInfoVO>> getUserInfoSubscribeList() {
        List<UserInfoBO> resBOList = userInfoService.getUserInfoSubscribeList();
        List<UserInfoVO> result = CommonConvert.INSTANCE.converToUserInfoVOList(resBOList);
        return ServerResponseEntity.success(result);
    }

}
