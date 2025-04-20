package com.example.productservice_proxyzee.services;

import com.example.productservice_proxyzee.models.es.ProductDocument;
import com.example.productservice_proxyzee.models.jpa.Product;
import com.example.productservice_proxyzee.repositries.elasticsearch.ProductElasticSearchRepo;
import com.example.productservice_proxyzee.repositries.jpa.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelfProductService implements IProductService{

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ProductElasticSearchRepo productElasticSearchRepo;
    @Override
    public Product getSingleProduct(Long productID) {
        return null;
    }

    @Override
    public List<Product> getAllProduct() {
        return null;
    }

    @Override
    public Product addNewProduct(Product product) {
        this.productRepo.save(product);//always save in db first since its the ultimate source of truth
        ProductDocument productDocument = toDocument(product);
        this.productElasticSearchRepo.save(productDocument);
        return product;
    }

    @Override
    public Product updateProduct(int productId, Product product) {
        return null;
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
