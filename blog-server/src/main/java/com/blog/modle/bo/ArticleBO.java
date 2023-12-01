package com.blog.modle.bo;

import com.core.modle.entity.Tag;
import com.core.modle.entity.UserInfo;
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
public class ArticleBO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    private Integer id;

    private UserInfo author;

    private String categoryName;

    private String articleCover;

    private String articleTitle;

    private String articleContent;

    private Integer isTop;

    private Integer isFeatured;

    private Integer isDelete;

    private Integer status;

    private List<Tag> tags;

    private Integer viewCount;

    private Integer type;

    private String originalUrl;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private ArticleCardBO preArticleCard;

    private ArticleCardBO nextArticleCard;

}
