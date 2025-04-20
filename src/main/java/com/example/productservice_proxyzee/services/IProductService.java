package com.example.productservice_proxyzee.services;


import com.example.productservice_proxyzee.models.jpa.Product;

import java.util.List;

public interface IProductService {

    Product getSingleProduct(Long productID);

    List<Product> getAllProduct();

    Product addNewProduct(Product product);

    Product updateProduct(int productId, Product product);
}
