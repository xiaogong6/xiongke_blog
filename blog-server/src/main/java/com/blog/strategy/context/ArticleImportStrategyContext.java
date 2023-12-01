package com.blog.strategy.context;


import com.api.enums.MarkdownTypeEnum;
import com.blog.strategy.ArticleImportStrategy;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author xiongke
 */
@Service
public class ArticleImportStrategyContext {

    @Resource
    private Map<String, ArticleImportStrategy> articleImportStrategyMap;

    public void importArticles(MultipartFile file, String type) {
        articleImportStrategyMap.get(MarkdownTypeEnum.getMarkdownType(type)).importArticles(file);
    }
}
