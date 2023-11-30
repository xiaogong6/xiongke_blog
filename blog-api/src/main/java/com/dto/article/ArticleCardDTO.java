package com.dto.article;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCardDTO {

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
