package com.blog.service;

import com.blog.modle.bo.UserInfoBO;

import java.util.List;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
public interface UserInfoService {

    List<UserInfoBO> getUserInfoSubscribeList();
}
