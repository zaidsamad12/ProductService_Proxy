package com.example.productservice_proxyzee.dtos;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequestDto {
    private String query;
    private int pageNumber;
    private int sizeOfPage;
    List<SortParamsDto> sortParamsList;
}
