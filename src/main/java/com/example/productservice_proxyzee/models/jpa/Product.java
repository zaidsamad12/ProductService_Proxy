package com.example.productservice_proxyzee.models.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Setter
@Getter
@Document(indexName = "productservice")
public class Product extends BaseModel {
    private String title;
    private double price;
    private String description;
    @ManyToOne
    private Categories category;
    private String imageUrl;
}
