package com.api.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiongke
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {

    private Integer id;

    private AuthorDTO author;

    private String categoryName;

    private String articleCover;

    private String articleTitle;

    private String articleContent;

    private Integer isTop;

    private Integer isFeatured;

    private Integer isDelete;

    private Integer status;

    private List<ArticleTagDTO> tags;

    private Integer viewCount;

    private Integer type;

    private String originalUrl;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private ArticleCardDTO preArticleCard;

    private ArticleCardDTO nextArticleCard;

}
