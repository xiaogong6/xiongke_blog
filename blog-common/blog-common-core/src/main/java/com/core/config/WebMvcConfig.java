package com.core.config;

import com.core.interceptor.AccessLimitInterceptor;
import com.core.interceptor.PaginationInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xiongke
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private PaginationInterceptor paginationInterceptor;

    @Resource
    private AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowedMethods("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(paginationInterceptor);
        registry.addInterceptor(accessLimitInterceptor);
    }

}
