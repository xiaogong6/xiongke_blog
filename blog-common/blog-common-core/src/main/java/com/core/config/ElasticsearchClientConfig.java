package com.core.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Program: xiongke_blog
 * @Description:
 * @Author: xiongke
 * @Create: 2023-12-09
 */
@Configuration
public class ElasticsearchClientConfig {

    @Value("${ip2}")
    private String ip;

    @Bean
    public ElasticsearchClient restHighLevelClient() {
        RestClient restClient = RestClient.builder(
                new HttpHost(ip, 9200)
        ).build();
        ElasticsearchTransport elasticsearchTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(elasticsearchTransport);
    }

}
