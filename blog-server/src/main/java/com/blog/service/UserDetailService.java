package com.blog.service;


import com.api.dto.user.UserDetailsDTO;
import com.core.modle.entity.UserAuth;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @Program: aurora-springboot
 * @Description:
 * @Author: xiongke
 * @Create: 2023-11-24
 */
public interface UserDetailService {

    UserDetailsDTO convertUserDetail(UserAuth user, HttpServletRequest request);

}
