package com.example.productservice_proxyzee.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public RestHighLevelClient client() {
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        return new RestHighLevelClient(builder);
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }
}

