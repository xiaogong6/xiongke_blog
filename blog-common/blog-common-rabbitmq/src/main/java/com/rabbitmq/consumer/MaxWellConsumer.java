package com.rabbitmq.consumer;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.alibaba.fastjson.JSON;
import com.api.dto.other.MaxwellDataDTO;
import com.core.modle.entity.Article;
import com.core.modle.es.ArticleSearch;
import com.core.util.BeanCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.api.constant.CommonConstant.INDEX;
import static com.api.constant.RabbitMQConstant.MAXWELL_QUEUE;


/**
 * @author xiongke
 */
@Component
@RabbitListener(queues = MAXWELL_QUEUE)
public class MaxWellConsumer {

    private final Logger logger = LoggerFactory.getLogger(MaxWellConsumer.class);

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @RabbitHandler
    public void process(byte[] data) {
        MaxwellDataDTO maxwellDataDTO = JSON.parseObject(new String(data), MaxwellDataDTO.class);
        Article article = JSON.parseObject(JSON.toJSONString(maxwellDataDTO.getData()), Article.class);
        ArticleSearch articleSearch = BeanCopyUtil.copyObject(article, ArticleSearch.class);
        switch (maxwellDataDTO.getType()) {
            case "insert":
            case "update":
                IndexRequest<ArticleSearch> indexRequest = new IndexRequest.Builder<ArticleSearch>()
                        .index(INDEX)
                        .document(articleSearch)
                        .build();
                try {
                    elasticsearchClient.index(indexRequest);
                } catch (IOException e) {
                    logger.error("MaxWellConsumer方法向ES插入文档失败,文档内容:{}", JSON.toJSONString(articleSearch));
                }
                // elasticsearchMapper.save();
                break;
            case "delete":
                try {
                    DeleteResponse deleteResponse = elasticsearchClient.delete(d -> d
                            .index(INDEX)
                            .id(String.valueOf(articleSearch.getId()))
                    );
                } catch (IOException e) {
                    logger.error("MaxWellConsumer方法向ES删除文档失败,文档内容:{}", JSON.toJSONString(articleSearch));
                }
                // elasticsearchMapper.deleteById(article.getId());
                break;
            default:
                break;
        }
    }
}