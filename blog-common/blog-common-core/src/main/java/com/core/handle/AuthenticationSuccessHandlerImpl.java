package com.core.handle;

import com.alibaba.fastjson.JSON;
import com.api.constant.CommonConstant;
import com.api.dto.user.UserDetailsDTO;
import com.api.dto.user.UserInfoDTO;
import com.api.feign.TokenClient;
import com.api.response.ServerResponseEntity;
import com.api.vo.base.ResultVO;
import com.core.mapper.UserAuthMapper;
import com.core.modle.entity.UserAuth;
import com.core.util.BeanCopyUtil;
import com.core.util.UserUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;


/**
 * @author xiongke
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Resource
    private UserAuthMapper userAuthMapper;

    @Resource
    private TokenClient tokenClient;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserInfoDTO userLoginDTO = BeanCopyUtil.copyObject(UserUtil.getUserDetailsDTO(), UserInfoDTO.class);
        if (Objects.nonNull(authentication)) {
            UserDetailsDTO userDetailsDTO = (UserDetailsDTO) authentication.getPrincipal();
            ServerResponseEntity<String> responseEntity = tokenClient.createToken(userDetailsDTO);
            userLoginDTO.setToken(responseEntity.getData());
        }
        response.setContentType(CommonConstant.APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(ResultVO.ok(userLoginDTO)));
        updateUserInfo();
    }

    @Async
    public void updateUserInfo() {
        UserAuth userAuth = UserAuth.builder()
                .id(UserUtil.getUserDetailsDTO().getId())
                .ipAddress(UserUtil.getUserDetailsDTO().getIpAddress())
                .ipSource(UserUtil.getUserDetailsDTO().getIpSource())
                .lastLoginTime(UserUtil.getUserDetailsDTO().getLastLoginTime())
                .build();
        userAuthMapper.updateById(userAuth);
    }
}
