package com.blog.service;

import com.api.dto.base.PageResultDTO;
import com.api.dto.user.UserInfoDTO;
import com.api.dto.user.UserOnlineDTO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.EmailVO;
import com.api.vo.other.SubscribeVO;
import com.api.vo.user.UserDisableVO;
import com.api.vo.user.UserInfoVO;
import com.api.vo.user.UserRoleVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.modle.bo.UserInfoBO;
import com.core.modle.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
public interface UserInfoService extends IService<UserInfo> {

    List<UserInfoBO> getUserInfoSubscribeList();

    void updateUserInfo(UserInfoVO userInfoVO);

    String updateUserAvatar(MultipartFile file);

    void saveUserEmail(EmailVO emailVO);

    void updateUserSubscribe(SubscribeVO subscribeVO);

    void updateUserRole(UserRoleVO userRoleVO);

    void updateUserDisable(UserDisableVO userDisableVO);

    PageResultDTO<UserOnlineDTO> listOnlineUsers(ConditionVO conditionVO);

    void removeOnlineUser(Integer userInfoId);

    UserInfoDTO getUserInfoById(Integer id);
}
