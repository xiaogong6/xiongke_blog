package com.blog.controller;

import com.api.feign.RedisClient;
import com.api.response.ServerResponseEntity;
import com.blog.service.RedisService;
import io.swagger.annotations.Api;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-03
 */
@Api(tags = "redis-api")
@RestController
public class RedisController implements RedisClient {

    @Resource
    private RedisService redisService;

    @Override
    public ServerResponseEntity<Long> incrExpire(String key, long time) {
        return ServerResponseEntity.success(redisService.incrExpire(key, time));
    }
}
