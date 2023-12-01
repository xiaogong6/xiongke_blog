package com.blog.strategy.context;

import com.blog.strategy.SearchStrategy;
import com.core.modle.es.ArticleSearch;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.api.enums.SearchModeEnum.getStrategy;


/**
 * @author xiongke
 */
@Service
public class SearchStrategyContext {

    @Value("${search.mode}")
    private String searchMode;

    @Resource
    private Map<String, SearchStrategy> searchStrategyMap;

    public List<ArticleSearch> executeSearchStrategy(String keywords) {
        return searchStrategyMap.get(getStrategy(searchMode)).searchArticle(keywords);
    }

}
