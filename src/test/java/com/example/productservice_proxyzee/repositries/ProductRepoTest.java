package com.example.productservice_proxyzee.repositries;

import com.example.productservice_proxyzee.models.Categories;
import com.example.productservice_proxyzee.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepoTest {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;

    @Test
    void save() {
    }

    @Test
    void saveProductAndCategories() {
        Categories categories = new Categories();
        categories.setName("Elect");
        categories.setDescription("DESC");
        categoryRepo.save(categories);

        Product product = new Product();
        product.setTitle("Lappy");
        product.setDescription("Lappy");
        product.setCategory(categories);
        product.setPrice(20);
        productRepo.save(product);

    }
}