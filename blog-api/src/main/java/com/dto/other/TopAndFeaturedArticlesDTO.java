package com.dto.other;

import com.dto.article.ArticleCardDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopAndFeaturedArticlesDTO {

    private ArticleCardDTO topArticle;

    private List<ArticleCardDTO> featuredArticles;
}
