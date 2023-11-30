package com.api.dto.aurora;

import com.api.dto.article.ArticleRankDTO;
import com.api.dto.article.ArticleStatisticsDTO;
import com.api.dto.category.CategoryDTO;
import com.api.dto.other.UniqueViewDTO;
import com.api.dto.tag.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuroraAdminInfoDTO {

    private Integer viewsCount;

    private Integer messageCount;

    private Integer userCount;

    private Integer articleCount;

    private List<CategoryDTO> categoryDTOs;

    private List<TagDTO> tagDTOs;

    private List<ArticleStatisticsDTO> articleStatisticsDTOs;

    private List<UniqueViewDTO> uniqueViewDTOs;

    private List<ArticleRankDTO> articleRankDTOs;

}
