package com.blog.repo;

import com.core.mapper.UserAuthMapper;
import com.core.modle.entity.UserAuth;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Repository
public class UserAuthRepo {

    @Resource
    private UserAuthMapper userAuthMapper;

    public void updateById(UserAuth userAuth) {
        userAuthMapper.updateById(userAuth);
    }
}
