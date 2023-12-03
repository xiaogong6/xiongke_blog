package com.rabbitmq.convert;

import com.api.dto.article.ArticleDTO;
import com.core.modle.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-01
 */
@Mapper
public interface RabbitMqConvert {

    RabbitMqConvert INSTANCE = Mappers.getMapper(RabbitMqConvert.class);

    Article converToArticle(ArticleDTO data);
}
