package com.core.config;

/**
 * @Program: aurora-springboot
 * @Description:
 * @Author: xiongke
 * @Create: 2023-11-22
 */

import com.core.config.properties.SwaggerProperty;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Description Swagger配置文件
 * @Author xiongke
 * @ModifyDate 2023/11/22 14:35
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperty.class)
public class SwaggerConfig {

    @Resource
    private SwaggerProperty swaggerProperty;

    @Bean
    public Docket createApiDoc() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName(swaggerProperty.getGroupName())
                .enable(swaggerProperty.getEnable())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperty.getScanPackage()))
                .build().directModelSubstitute(Timestamp.class, Date.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperty.getTitle())
                .description(swaggerProperty.getDescribe())
                .version(swaggerProperty.getVersion())
                .termsOfServiceUrl(swaggerProperty.getTermsOfServiceUrl())
                .contact(new Contact(swaggerProperty.getContactName(), swaggerProperty.getContactUrl()
                        , swaggerProperty.getContactEmail()))
                .build();
    }
}
