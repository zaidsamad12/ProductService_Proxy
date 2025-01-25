package com.example.productservice_proxyzee.repositries;

import com.example.productservice_proxyzee.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Categories, Long> {

    Categories save(Categories categories);

}
