package com.blog.strategy;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiongke
 */
public interface ArticleImportStrategy {

    void importArticles(MultipartFile file);

}
