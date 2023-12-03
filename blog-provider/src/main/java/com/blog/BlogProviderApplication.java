package com.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author xiongke
 */
@EnableDiscoveryClient
@SpringBootApplication()
@MapperScan("com.core.mapper")
@ComponentScan(basePackages = {"com.core", "com.api.feign"})
public class BlogProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogProviderApplication.class, args);
    }

}
