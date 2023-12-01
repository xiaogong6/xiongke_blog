package com.api.feign;

import com.api.response.ServerResponseEntity;
import com.api.vo.user.UserAuthVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@FeignClient(name = "blog-provider", contextId = "UserAuthClient", path = "/api/userAuth")
public interface UserAuthClient {

    @PostMapping(value = "/updateById")
    ServerResponseEntity<?> updateById(@RequestBody UserAuthVO userAuthVO);

}
