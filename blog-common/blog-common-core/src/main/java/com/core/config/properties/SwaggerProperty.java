package com.core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Program: aurora-springboot
 * @Description:
 * @Author: xiongke
 * @Create: 2023-11-22
 */
@Data
@Configuration
@ConfigurationProperties(value = "swagger")
public class SwaggerProperty {

    // 系统标题
    private String title;
    // 分组名称
    private String groupName;
    // 是否开启
    private Boolean enable = true;
    // 描述信息
    private String describe;
    // 版本信息
    private String version;
    // 扫描路径
    private String scanPackage;
    // 组织链接
    private String termsOfServiceUrl;
    // 联系人名称
    private String contactName;
    // 联系人url
    private String contactUrl;
    // 联系人email
    private String contactEmail;

}


