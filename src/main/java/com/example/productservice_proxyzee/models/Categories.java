package com.example.productservice_proxyzee.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Categories extends BaseModel {
    private String name;
    private String description;
    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<Product> productList;

}
