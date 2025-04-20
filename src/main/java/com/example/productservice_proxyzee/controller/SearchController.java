package com.example.productservice_proxyzee.controller;

import com.example.productservice_proxyzee.dtos.ProductResponseDto;
import com.example.productservice_proxyzee.dtos.SearchRequestDto;
import com.example.productservice_proxyzee.models.es.ProductDocument;
import com.example.productservice_proxyzee.models.jpa.Product;
import com.example.productservice_proxyzee.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @PostMapping
    public List<ProductResponseDto> searchProducts(@RequestBody SearchRequestDto searchRequestDto){
        List<ProductDocument> productDocumentList = searchService.searchProduct(searchRequestDto.getQuery(), searchRequestDto.getPageNumber(),
                searchRequestDto.getSizeOfPage(), searchRequestDto.getSortParamsList());
        return getProductDocumentDto(productDocumentList);
    }

    @PostMapping("/syncDBWithES")
    public ResponseEntity<String> syncDBWithES() {
        if(searchService.syncDBWithES())
            return new ResponseEntity<>("Sync Success", HttpStatus.OK);
        else
            return new ResponseEntity<>("Sync Failed", HttpStatus.BAD_REQUEST);
    }
    private List<ProductResponseDto> getProductDocumentDto(List<ProductDocument> productDocumentList) {
        return productDocumentList.stream().map(product -> {
                    ProductResponseDto productResponseDto = new ProductResponseDto();
                    //productResponseDto.setId(product.getId().t);
                    productResponseDto.setPrice(product.getPrice());
                    productResponseDto.setDescription(product.getDescription());
                    productResponseDto.setCategory(product.getCategoryName());
                    return productResponseDto;
                })
                .collect(Collectors.toList());
    }
}
