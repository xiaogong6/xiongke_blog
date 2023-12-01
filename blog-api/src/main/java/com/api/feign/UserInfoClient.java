package com.api.feign;

import com.api.response.ServerResponseEntity;
import com.api.vo.user.UserInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@FeignClient(name = "blog-provider", contextId = "UserInfoClient", path = "/api/userInfo")
public interface UserInfoClient {

    @GetMapping(value = "/getUserInfoSubscribeList")
    ServerResponseEntity<List<UserInfoVO>> getUserInfoSubscribeList();

}
