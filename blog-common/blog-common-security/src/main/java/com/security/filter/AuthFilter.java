package com.security.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.api.constant.Auth;
import com.api.dto.user.UserDetailsDTO;
import com.api.feign.TokenClient;
import com.api.response.ResponseEnum;
import com.api.response.ServerResponseEntity;
import com.core.handle.HttpHandler;
import com.core.util.UserUtil;
import com.security.adapter.AuthConfigAdapter;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 授权过滤，只要实现AuthConfigAdapter接口，添加对应路径即可：
 *
 * @author FrozenWatermelon
 * @date 2020/7/11
 */
@Component
public class AuthFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private AuthConfigAdapter authConfigAdapter;

    @Autowired
    private HttpHandler httpHandler;

    @Resource
    private TokenClient tokenClient;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (Auth.CHECK_TOKEN_URI.equals(req.getRequestURI())) {
            chain.doFilter(req, resp);
            return;
        }

        List<String> excludePathPatterns = authConfigAdapter.excludePathPatterns();

        // 如果匹配不需要授权的路径，就不需要校验是否需要授权
        if (CollectionUtil.isNotEmpty(excludePathPatterns)) {
            for (String excludePathPattern : excludePathPatterns) {
                AntPathMatcher pathMatcher = new AntPathMatcher();
                if (pathMatcher.match(excludePathPattern, req.getRequestURI())) {
                    chain.doFilter(req, resp);
                    return;
                }
            }
        }

        String accessToken = req.getHeader("Authorization");

        if (StrUtil.isBlank(accessToken)) {
            httpHandler.printServerResponseToWeb(ServerResponseEntity.fail(ResponseEnum.UNAUTHORIZED));
            return;
        }

        // 校验token，并返回用户信息
        ServerResponseEntity<UserDetailsDTO> responseEntity = tokenClient.getUserDetailDTO(req);
        if (!responseEntity.isSuccess()) {
            logger.error("tokenClient远程条用接口异常:{}", JSON.toJSONString(responseEntity));
            httpHandler.printServerResponseToWeb(ServerResponseEntity.fail(ResponseEnum.UNAUTHORIZED));
            return;
        }
        UserDetailsDTO entityData = responseEntity.getData();
        if (Objects.isNull(entityData) || Objects.isNull(UserUtil.getAuthentication())) {
            logger.error("校验token失败,用户信息异常");
            return;
        }
        try {
            // 刷新token
            tokenClient.renewToken(entityData);
            // 保存上下文
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(entityData, null, entityData.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(req, resp);
        } finally {
            SecurityContextHolder.clearContext();
        }

    }


}
