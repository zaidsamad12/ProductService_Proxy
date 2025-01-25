package com.example.productservice_proxyzee.repositries;

import com.example.productservice_proxyzee.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product save(Product product);
}
