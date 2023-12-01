package com.blog.service;

import com.api.dto.base.PageResultDTO;
import com.api.dto.user.UserAdminDTO;
import com.api.dto.user.UserAreaDTO;
import com.api.dto.user.UserInfoDTO;
import com.api.dto.user.UserLogoutStatusDTO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.PasswordVO;
import com.api.vo.other.QQLoginVO;
import com.api.vo.user.UserVO;
import com.blog.modle.bo.UserAuthBO;

import java.util.List;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
public interface UserAuthService {
    void sendCode(String username);

    List<UserAreaDTO> listUserAreas(ConditionVO conditionVO);

    PageResultDTO<UserAdminDTO> listUsers(ConditionVO conditionVO);

    void register(UserVO userVO);

    void updatePassword(UserVO user);

    void updateAdminPassword(PasswordVO passwordVO);

    UserLogoutStatusDTO logout();

    UserInfoDTO qqLogin(QQLoginVO qqLoginVO);

    void updateById(UserAuthBO userAuthBO);
}
