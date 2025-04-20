package com.example.productservice_proxyzee.services;

import com.example.productservice_proxyzee.models.es.ProductDocument;
import com.example.productservice_proxyzee.models.jpa.Product;
import com.example.productservice_proxyzee.dtos.SortParamsDto;
import com.example.productservice_proxyzee.repositries.elasticsearch.ProductElasticSearchRepo;
import com.example.productservice_proxyzee.repositries.jpa.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    ProductElasticSearchRepo productElasticSearchRepo;
    @Autowired
    ProductRepo productRepo;

    public boolean syncDBWithES(){
        List<Product> allProducts = productRepo.findAll();
        List<ProductDocument> productDocuments = allProducts.stream()
                .map(this::toDocument)
                .collect(Collectors.toList());
        for (ProductDocument doc : productDocuments) {
            productElasticSearchRepo.save(doc);
        }
        return true;
    }

    public List<ProductDocument> searchProduct(String query, int pageNumber, int pageSize,
                                       List<SortParamsDto> sortParamsList){
        //Sort sort = Sort.by("title").descending().and(Sort.by("price"));
        /*Sort sort = null;
        for(SortParamsDto sortParams : sortParamsList){
            sort = Sort.by(sortParams.getParamName()).and(Sort.by(sortParams.getSortType()));
        }*/

        String sortField = sortParamsList.get(0).getParamName();
        if (sortField.equals("title")) {
            sortField = "title.keyword"; // only apply keyword mapping where needed
        }

        Sort sort=null;
        if(sortParamsList.get(0).getSortType().equals("ASC"))
            sort = Sort.by(sortField);
        else
            sort = Sort.by(sortField).descending();

        for(int i=1; i<sortParamsList.size(); i++) {
            sortField = sortParamsList.get(i).getParamName();
            if (sortField.equals("title")) {
                sortField = "title.keyword"; // only apply keyword mapping where needed
            }
            if (sortParamsList.get(i).getSortType().equals("ASC"))
                sort = sort.and(Sort.by(sortField));
            else
                sort = sort.and(Sort.by(sortField)).descending();
        }

        Pageable firstPageWithTwoElements = PageRequest.of(pageNumber, pageSize, sort);
        List<ProductDocument> prodList = productElasticSearchRepo.findProductDocumentByTitleEquals(query, firstPageWithTwoElements);
        //System.out.println(prodList.toString());
        return prodList;
    }

    public ProductDocument toDocument(Product product) {
        ProductDocument doc = new ProductDocument();
        doc.setId(product.getId().toString());
        doc.setTitle(product.getTitle());
        doc.setDescription(product.getDescription());
        doc.setPrice(product.getPrice());
        doc.setImageUrl(product.getImageUrl());
        doc.setCategoryName(product.getCategory().getName());
        return doc;
    }
}
