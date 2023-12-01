package com.api.dto.article;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-11-30
 */
@Data
public class AuthorDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    private Integer id;

    private String email;

    private String nickname;

    private String avatar;

    private String intro;

    private String website;

    private Integer isSubscribe;

    private Integer isDisable;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
