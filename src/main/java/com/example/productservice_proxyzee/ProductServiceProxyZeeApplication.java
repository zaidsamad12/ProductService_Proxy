package com.example.productservice_proxyzee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        exclude = {
                org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration.class
        }
)

@EnableCaching
@EnableJpaRepositories(basePackages = "com.example.productservice_proxyzee.repositries.jpa")
@EnableElasticsearchRepositories(basePackages = "com.example.productservice_proxyzee.repositries.elasticsearch")
public class ProductServiceProxyZeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceProxyZeeApplication.class, args);
    }

}
