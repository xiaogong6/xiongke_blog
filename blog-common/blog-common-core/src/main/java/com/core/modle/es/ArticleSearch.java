package com.core.modle.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


/**
 * @Program: my_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-11-30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// @Document(indexName = "article")
public class ArticleSearch {

    @Id
    private Integer id;

    private String articleTitle;

    private String articleContent;

    private Integer isDelete;

    private Integer status;

}
