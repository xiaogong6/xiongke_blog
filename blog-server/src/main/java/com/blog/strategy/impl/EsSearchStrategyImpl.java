package com.blog.strategy.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.blog.strategy.SearchStrategy;
import com.core.modle.es.ArticleSearch;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.api.constant.CommonConstant.*;
import static com.api.enums.ArticleStatusEnum.PUBLIC;


/**
 * @author xiongke
 */
@Log4j2
@Service("esSearchStrategyImpl")
public class EsSearchStrategyImpl implements SearchStrategy {

    @Autowired
    private ElasticsearchClient elasticsearchClient;


    @Override
    public List<ArticleSearch> searchArticle(String keywords) {
        if (StringUtils.isBlank(keywords)) {
            return new ArrayList<>();
        }
        return search(buildRequest(keywords));
    }

    private SearchRequest buildRequest(String keywords) {
        SearchRequest.Builder builder = getBuilder(keywords);
        // 字段高亮显示
        Highlight.Builder highlightBuilder = new Highlight.Builder();
        highlightBuilder.fields("articleTitle", f -> f.preTags(PRE_TAG).postTags(POST_TAG))
                .fields("articleContent", f -> f.preTags(PRE_TAG).postTags(POST_TAG)
                        .fragmentSize(50)).requireFieldMatch(false);
        builder.highlight(highlightBuilder.build());
        return builder.build();
    }

    @NotNull
    private SearchRequest.Builder getBuilder(String keywords) {
        SearchRequest.Builder builder = new SearchRequest.Builder();
        // 添加索引
        builder.index(INDEX);
        // 构建查询条件
        BoolQuery.Builder boolQuery = new BoolQuery.Builder();
        boolQuery.must(q -> q.bool(b -> b
                .should(h -> h.match(f -> f.field("articleTitle").query(keywords)))
                .should(h -> h.match(f -> f.field("articleContent").query(keywords)))
                .must(h -> h.match(f -> f.field("isDelete").query(FALSE)))
                .must(h -> h.match(f -> f.field("status").query(PUBLIC.getStatus())))));
        builder.query(q -> q.bool(boolQuery.build()));
        return builder;
    }

    private List<ArticleSearch> search(SearchRequest searchRequest) {
        try {
            SearchResponse<ArticleSearch> response = elasticsearchClient.search(searchRequest, ArticleSearch.class);
            return response.hits().hits().stream().map(h -> {
                ArticleSearch article = h.source();
                if (Objects.isNull(article)) {
                    return new ArticleSearch();
                }
                List<String> articleTitle = h.highlight().get("articleTitle");
                if (CollectionUtils.isNotEmpty(articleTitle)) {
                    article.setArticleTitle(articleTitle.get(0));
                }
                List<String> articleContent = h.highlight().get("articleContent");
                if (CollectionUtils.isNotEmpty(articleContent)) {
                    article.setArticleContent(articleContent.get(articleContent.size() - 1));
                }
                return article;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Es查询文档失败,请求为:{}", JSON.toJSONString(searchRequest));
        }
        return Collections.emptyList();
    }
}

