package com.api.dto.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiongke
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSearchDTO {

    private Integer id;

    private String articleTitle;

    private String articleContent;

    private Integer isDelete;

    private Integer status;

}
