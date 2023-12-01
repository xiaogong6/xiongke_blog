package com.blog.repo;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.core.mapper.UserInfoMapper;
import com.core.modle.entity.UserInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Repository
public class UserInfoRepo {

    @Resource
    private UserInfoMapper userInfoMapper;

    public List<UserInfo> getList() {
        return userInfoMapper.selectList(Wrappers.<UserInfo>lambdaQuery()
                .eq(UserInfo::getIsSubscribe, Boolean.TRUE));
    }
}
