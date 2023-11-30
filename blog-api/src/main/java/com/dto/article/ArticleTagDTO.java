package com.dto.article;

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
public class ArticleTagDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    private Integer id;

    private String tagName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
