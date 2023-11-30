package com.dto.aurora;

import com.dto.category.CategoryDTO;
import com.dto.tag.TagDTO;
import com.dto.other.UniqueViewDTO;
import com.dto.article.ArticleRankDTO;
import com.dto.article.ArticleStatisticsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AuroraBackInfoDTO {

    private Integer viewsCount;

    private Integer messageCount;

    private Integer userCount;

    private Integer articleCount;

    private List<CategoryDTO> categoryDTOList;

    private List<TagDTO> tagDTOList;

    private List<ArticleStatisticsDTO> articleStatisticsList;

    private List<UniqueViewDTO> uniqueViewDTOList;

    private List<ArticleRankDTO> articleRankDTOList;

}
