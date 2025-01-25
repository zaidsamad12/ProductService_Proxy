package com.example.productservice_proxyzee.clients.fakestore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FakeStoreDto {
        private Long id;
        private String title;
        private double price;
        private String description;
        private String image;
        private String category;
}
