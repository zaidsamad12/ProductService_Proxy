package com.example.productservice_proxyzee.services;


import com.example.productservice_proxyzee.dtos.ProductRequestDto;
import com.example.productservice_proxyzee.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductService {

    Product getSingleProduct(Long productID);

    List<Product> getAllProduct();

    Product addNewProduct(ProductRequestDto productRequestDto);

    Product updateProduct(int productId, ProductRequestDto productRequestDto);
}
