package com.blog.modle.bo;

import com.api.dto.article.ArticleTagDTO;
import com.api.dto.article.AuthorDTO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Data
public class ArticleCardBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    private Integer id;

    private String articleCover;

    private String articleTitle;

    private String articleContent;

    private Integer isTop;

    private Integer isFeatured;

    private AuthorDTO author;

    private String categoryName;

    private List<ArticleTagDTO> tags;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
