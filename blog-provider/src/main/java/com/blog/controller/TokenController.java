package com.blog.controller;

import com.api.dto.user.UserDetailsDTO;
import com.api.feign.TokenClient;
import com.blog.service.TokenService;
import com.api.response.ServerResponseEntity;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
public class TokenController implements TokenClient {

    @Resource
    private TokenService tokenService;

    @Override
    public ServerResponseEntity<UserDetailsDTO> getUserDetailDTO(HttpServletRequest request) {
        UserDetailsDTO userDetailsDTO = tokenService.getUserDetailInfo(request);
        return ServerResponseEntity.success(userDetailsDTO);
    }

    @Override
    public ServerResponseEntity<?> renewToken(UserDetailsDTO userDetailsDTO) {
        tokenService.renewToken(userDetailsDTO);
        return ServerResponseEntity.success();
    }

    @Override
    public ServerResponseEntity<String> createToken(UserDetailsDTO userDetailsDTO) {
        String token = tokenService.createToken(userDetailsDTO);
        return ServerResponseEntity.success(token);
    }
}
