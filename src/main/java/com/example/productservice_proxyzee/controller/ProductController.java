package com.example.productservice_proxyzee.controller;

import com.example.productservice_proxyzee.dtos.ProductRequestDto;
import com.example.productservice_proxyzee.dtos.ProductResponseDto;
import com.example.productservice_proxyzee.models.jpa.Categories;
import com.example.productservice_proxyzee.models.jpa.Product;
import com.example.productservice_proxyzee.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    @Qualifier("selfProductService")
    IProductService productService;

    @GetMapping("product/{productID}")
    public ResponseEntity<ProductResponseDto> getSingleProduct(@PathVariable("productID") Long productID) {
        Product product = productService.getSingleProduct(productID);
        return new ResponseEntity<>(getProductDtoFromProdcut(product), HttpStatus.OK);
    }

    @GetMapping("products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<Product> products = productService.getAllProduct();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        try {
            if (products != null) {
                products.forEach(product ->
                        productResponseDtoList.add(getProductDtoFromProdcut(product)));
                return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("products")
    public ResponseEntity<ProductResponseDto> addNewProduct(@RequestBody ProductRequestDto productRequestDto) {
        try {
            Product productRequest = getProduct(productRequestDto);
            Product product = productService.addNewProduct(productRequest);
            ProductResponseDto productResponseDto = getProductDtoFromProdcut(product);
            return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    private ProductResponseDto getProductDtoFromProdcut(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setImage(product.getImageUrl());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setTitle(product.getTitle());
        Categories categories = new Categories();
        categories.setDescription(product.getCategory().getDescription());
        categories.setName(product.getCategory().getName());
        categories.setId(product.getCategory().getId());
        productResponseDto.setCategory(categories.toString());
        return productResponseDto;
    }

    @PutMapping("/{productID}")
    public String updateProduct(@PathVariable("productID") Long productID) {
        return "updated the product";
    }

    @DeleteMapping("/{productID}")
    public void deleteProduct(@PathVariable("productID") Long productID) {
    }

    @PatchMapping("products/{productID}")
    public ResponseEntity<ProductResponseDto> patchProduct(@PathVariable("productID") int productId,
                                                           @RequestBody ProductRequestDto productRequestDto) {
        try {
            Product productRequest = getProduct(productRequestDto);
            Product product = productService.updateProduct(productId, productRequest);
            ProductResponseDto productResponseDto = getProductDtoFromProdcut(product);
            return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    private Product getProduct(ProductRequestDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Categories category = new Categories();
        category.setName(productDto.getCategory());
        product.setCategory(category);
        product.setImageUrl(productDto.getImage());
        product.setDescription(productDto.getDescription());
        return product;
    }

}
