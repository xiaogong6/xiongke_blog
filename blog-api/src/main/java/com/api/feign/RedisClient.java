package com.api.feign;

import com.api.response.ServerResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@FeignClient(name = "blog-provider", contextId = "RedisClient", path = "/api/redis")
public interface RedisClient {

    @GetMapping(value = "/incrExpire")
    ServerResponseEntity<Long> incrExpire(@RequestParam("key") String key, @RequestParam("time") long time);

}
