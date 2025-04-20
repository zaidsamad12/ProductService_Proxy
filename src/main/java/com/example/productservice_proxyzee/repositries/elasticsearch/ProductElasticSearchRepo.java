package com.example.productservice_proxyzee.repositries.elasticsearch;

import com.example.productservice_proxyzee.models.es.ProductDocument;
import com.example.productservice_proxyzee.models.jpa.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductElasticSearchRepo extends ElasticsearchRepository<ProductDocument, String> {

    ProductDocument save(ProductDocument product);
    List<ProductDocument> findProductDocumentByTitleEquals(String query, Pageable pageable);
}
