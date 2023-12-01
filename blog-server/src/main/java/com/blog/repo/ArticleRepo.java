package com.blog.repo;

import com.core.mapper.ArticleMapper;
import com.core.modle.entity.Article;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Repository
public class ArticleRepo {

    @Resource
    private ArticleMapper articleMapper;

    public Article getArticleById(Integer articleId) {
        return articleMapper.selectById(articleId);
    }
}
