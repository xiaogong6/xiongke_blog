package com.blog.service.impl;


import com.api.dto.user.UserDetailsDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.blog.service.UserDetailService;
import com.core.exception.BizException;
import com.core.mapper.RoleMapper;
import com.core.mapper.UserAuthMapper;
import com.core.mapper.UserInfoMapper;
import com.core.modle.entity.UserAuth;
import com.core.modle.entity.UserInfo;
import com.core.util.IpUtil;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @Program: aurora-springboot
 * @Description:
 * @Author: xiongke
 * @Create: 2023-11-24
 */
@Service
public class UserDetailServiceImp implements UserDetailService, UserDetailsService {

    @Resource
    private UserAuthMapper userAuthMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new BizException("用户名不能为空!");
        }
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getId, UserAuth::getUserInfoId, UserAuth::getUsername, UserAuth::getPassword, UserAuth::getLoginType)
                .eq(UserAuth::getUsername, username));
        if (Objects.isNull(userAuth)) {
            throw new BizException("用户不存在!");
        }
        return convertUserDetail(userAuth, request);
    }

    @Override
    public UserDetailsDTO convertUserDetail(UserAuth user, HttpServletRequest request) {
        UserInfo userInfo = userInfoMapper.selectById(user.getUserInfoId());
        List<String> roles = roleMapper.listRolesByUserInfoId(userInfo.getId());
        String ipAddress = IpUtil.getIpAddress(request);
        String ipSource = IpUtil.getIpSource(ipAddress);
        UserAgent userAgent = IpUtil.getUserAgent(request);
        return UserDetailsDTO.builder()
                .id(user.getId())
                .loginType(user.getLoginType())
                .userInfoId(userInfo.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(userInfo.getEmail())
                .roles(roles)
                .nickname(userInfo.getNickname())
                .avatar(userInfo.getAvatar())
                .intro(userInfo.getIntro())
                .website(userInfo.getWebsite())
                .isSubscribe(userInfo.getIsSubscribe())
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .isDisable(userInfo.getIsDisable())
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOperatingSystem().getName())
                .lastLoginTime(LocalDateTime.now())
                .build();
    }
}
