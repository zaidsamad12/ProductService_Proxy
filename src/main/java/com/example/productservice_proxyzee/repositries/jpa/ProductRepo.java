package com.example.productservice_proxyzee.repositries.jpa;

import com.example.productservice_proxyzee.models.jpa.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product save(Product product);

    List<Product> findProductByTitleEquals(String query, Pageable pageable);
}
