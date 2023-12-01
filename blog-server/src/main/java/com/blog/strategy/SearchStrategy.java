package com.blog.strategy;


import com.core.modle.es.ArticleSearch;

import java.util.List;

/**
 * @author xiongke
 */
public interface SearchStrategy {

    List<ArticleSearch> searchArticle(String keywords);

}
