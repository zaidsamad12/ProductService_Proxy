package com.example.productservice_proxyzee.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Product extends BaseModel {
    private String title;
    private double price;
    private String description;
    @ManyToOne
    private Categories category;
    private String imageUrl;
}
