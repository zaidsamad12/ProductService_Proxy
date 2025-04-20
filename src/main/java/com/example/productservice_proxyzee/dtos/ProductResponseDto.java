package com.example.productservice_proxyzee.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDto {
        private Long id;
        private String title;
        private double price;
        private String description;
        private String image;
        private String category;
}
