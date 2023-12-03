package com.rabbitmq.consumer;

import com.alibaba.fastjson.JSON;
import com.api.dto.other.MaxwellDataDTO;
import com.core.mapper.ElasticsearchMapper;
import com.core.modle.entity.Article;
import com.core.modle.es.ArticleSearch;
import com.core.util.BeanCopyUtil;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.api.constant.RabbitMQConstant.MAXWELL_QUEUE;


/**
 * @author xiongke
 */
@Component
@RabbitListener(queues = MAXWELL_QUEUE)
public class MaxWellConsumer {

    @Resource
    private ElasticsearchMapper elasticsearchMapper;

    @RabbitHandler
    public void process(byte[] data) {
        MaxwellDataDTO maxwellDataDTO = JSON.parseObject(new String(data), MaxwellDataDTO.class);
        Article article = JSON.parseObject(JSON.toJSONString(maxwellDataDTO.getData()), Article.class);
        switch (maxwellDataDTO.getType()) {
            case "insert":
            case "update":
                elasticsearchMapper.save(BeanCopyUtil.copyObject(article, ArticleSearch.class));
                break;
            case "delete":
                elasticsearchMapper.deleteById(article.getId());
                break;
            default:
                break;
        }
    }
}