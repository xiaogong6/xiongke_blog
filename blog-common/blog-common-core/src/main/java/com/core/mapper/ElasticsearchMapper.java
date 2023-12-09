package com.core.mapper;

import com.core.modle.es.ArticleSearch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * @author 城北花开
 * elasticsearch
 */
@Repository
public interface ElasticsearchMapper extends CrudRepository<ArticleSearch, Integer> {

}
