package com.example.productservice_proxyzee.repositries;

import com.example.productservice_proxyzee.repositries.jpa.CategoryRepo;
import com.example.productservice_proxyzee.repositries.jpa.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductRepoTest {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;

    /*@Test
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

    }*/
}