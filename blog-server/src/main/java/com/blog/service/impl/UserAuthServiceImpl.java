package com.blog.service.impl;

import com.api.dto.base.PageResultDTO;
import com.api.dto.user.UserAdminDTO;
import com.api.dto.user.UserAreaDTO;
import com.api.dto.user.UserInfoDTO;
import com.api.dto.user.UserLogoutStatusDTO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.PasswordVO;
import com.api.vo.other.QQLoginVO;
import com.api.vo.user.UserVO;
import com.blog.repo.UserAuthRepo;
import com.blog.convert.ServiceConvert;
import com.blog.modle.bo.UserAuthBO;
import com.core.modle.entity.UserAuth;
import com.blog.service.UserAuthService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Resource
    private UserAuthRepo userAuthRepo;

    @Override
    public void updateById(UserAuthBO userAuthBO) {
        UserAuth userAuth = ServiceConvert.INSTANCE.converToUserAuth(userAuthBO);
        userAuthRepo.updateById(userAuth);
    }

    @Override
    public void sendCode(String username) {

    }

    @Override
    public List<UserAreaDTO> listUserAreas(ConditionVO conditionVO) {
        return null;
    }

    @Override
    public PageResultDTO<UserAdminDTO> listUsers(ConditionVO conditionVO) {
        return null;
    }

    @Override
    public void register(UserVO userVO) {

    }

    @Override
    public void updatePassword(UserVO user) {

    }

    @Override
    public void updateAdminPassword(PasswordVO passwordVO) {

    }

    @Override
    public UserLogoutStatusDTO logout() {
        return null;
    }

    @Override
    public UserInfoDTO qqLogin(QQLoginVO qqLoginVO) {
        return null;
    }
}
