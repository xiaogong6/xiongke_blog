package com.core.handle;

import com.alibaba.fastjson.JSON;
import com.api.constant.CommonConstant;
import com.api.vo.base.ResultVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 登录失败处理
 *
 * @author xiongke
 */
@Component
public class AuthenticationFailHandlerImpl implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType(CommonConstant.APPLICATION_JSON);
        httpServletResponse.getWriter().write(JSON.toJSONString(ResultVO.fail(e.getMessage())));
    }

}
