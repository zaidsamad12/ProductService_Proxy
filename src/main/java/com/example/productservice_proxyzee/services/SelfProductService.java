package com.example.productservice_proxyzee.services;

import com.example.productservice_proxyzee.models.es.ProductDocument;
import com.example.productservice_proxyzee.models.jpa.Product;
import com.example.productservice_proxyzee.repositries.elasticsearch.ProductElasticSearchRepo;
import com.example.productservice_proxyzee.repositries.jpa.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import java.util.List;

@Service
public class SelfProductService implements IProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductElasticSearchRepo productElasticSearchRepo;

    // ✅ READ operation - Try Redis first
    @Cacheable(value = "products", key = "#productID")
    @Override
    public Product getSingleProduct(Long productID) {
        return productRepo.findById(productID).orElse(null);
    }

    // ✅ READ ALL - Cached version of product list
    @Cacheable(value = "allProducts")
    @Override
    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    // ✅ WRITE - Save to DB + ES, then evict "allProducts" cache
    @CacheEvict(value = "allProducts", allEntries = true)
    @Override
    public Product addNewProduct(Product product) {
        this.productRepo.save(product); // DB is source of truth
        ProductDocument productDocument = toDocument(product);
        this.productElasticSearchRepo.save(productDocument);
        return product;
    }

    // ✅ UPDATE - Update DB + ES, evict both individual & list cache
    @Caching(evict = {
            @CacheEvict(value = "products", key = "#productId"),
            @CacheEvict(value = "allProducts", allEntries = true)
    })
    @Override
    public Product updateProduct(int productId, Product product) {
        product.setId((long) productId); // Ensure ID is set
        Product updatedProduct = productRepo.save(product);
        ProductDocument doc = toDocument(updatedProduct);
        productElasticSearchRepo.save(doc);
        return updatedProduct;
    }

    public ProductDocument toDocument(Product product) {
        ProductDocument doc = new ProductDocument();
        doc.setId(product.getId().toString());
        doc.setTitle(product.getTitle());
        doc.setDescription(product.getDescription());
        doc.setPrice(product.getPrice());
        doc.setImageUrl(product.getImageUrl());
        doc.setCategoryName(product.getCategory().getName());
        return doc;
    }
}
