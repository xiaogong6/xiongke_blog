package com.security.constants;

import lombok.Getter;

import java.util.List;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Getter
public class FeignInsideAuthConfig {

    /**
     * feign请求前缀
     */
    public static final String FEIGN_INSIDE_URL_PREFIX = "/feign";

    private String key;

    private String secret;

    private List<String> ips;

    public void setKey(String key) {
        this.key = key;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    @Override
    public String toString() {
        return "FeignInsideAuthConfig{" +
                "key='" + key + '\'' +
                ", secret='" + secret + '\'' +
                ", ips=" + ips +
                '}';
    }

}
