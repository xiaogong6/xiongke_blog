package com.blog.modle.bo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Data
public class UserInfoBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    /**
     * 昵称不能为空
     */
    private String nickname;

    /**
     * 介绍
     */
    private String intro;

    /**
     * 个人网站
     */
    private String website;

    /**
     * 电子邮件
     */
    private String email;
}
