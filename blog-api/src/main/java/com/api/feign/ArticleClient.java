package com.api.feign;

import com.api.response.ServerResponseEntity;
import com.api.vo.article.ArticleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@FeignClient(name = "blog-provider", contextId = "ArticleClient", path = "/api/article")
public interface ArticleClient {

    @GetMapping(value = "/getArticleById")
    ServerResponseEntity<ArticleVO> getArticleById(@RequestParam("id") Integer id);

}
