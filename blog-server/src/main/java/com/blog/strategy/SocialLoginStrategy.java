package com.blog.strategy;


import com.api.dto.user.UserInfoDTO;

/**
 * @author xiongke
 */
public interface SocialLoginStrategy {

    UserInfoDTO login(String data);

}
