package com.blog.service.impl;

import com.blog.convert.ServiceConvert;
import com.blog.modle.bo.UserInfoBO;
import com.blog.repo.UserInfoRepo;
import com.blog.service.UserInfoService;
import com.core.modle.entity.UserInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoRepo userInfoRepo;

    @Override
    public List<UserInfoBO> getUserInfoSubscribeList() {
        List<UserInfo> list = userInfoRepo.getList();
        return ServiceConvert.INSTANCE.converUserInfoBOList(list);
    }
}
