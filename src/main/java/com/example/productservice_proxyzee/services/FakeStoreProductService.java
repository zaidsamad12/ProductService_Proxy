package com.example.productservice_proxyzee.services;

import com.example.productservice_proxyzee.clients.fakestore.client.FakeStoreClient;
import com.example.productservice_proxyzee.clients.fakestore.dto.FakeStoreDto;
import com.example.productservice_proxyzee.models.jpa.Categories;
import com.example.productservice_proxyzee.models.jpa.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    @Autowired
    FakeStoreClient fakeStoreClient;

    @Override
    //@Cacheable(value = "products", key = "#productID")
    public Product getSingleProduct(Long productID){
        Product product = null;
        try {
            FakeStoreDto fakeStoreDto = fakeStoreClient.getSingleProduct(productID);
            if(fakeStoreDto != null) {
                product = getProduct(fakeStoreDto);
            }
        } catch (RestClientException e) {
            System.out.println("Exception occuerred "+e.getMessage());
        }
        return  product;
    }

    @Override
    public List<Product> getAllProduct(){
        List<Product> productList = new ArrayList<>();
        try {
            List<FakeStoreDto> fakeStoreResultList = fakeStoreClient.getAllProduct();
            fakeStoreResultList.forEach(fakeStore -> productList.add(getProduct(fakeStore)));
        } catch (RestClientException e) {
            System.out.println("RestClientException occuerred "+e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Exception occuerred "+e.getMessage());
        }
        return productList;
    }

    @Override
    public Product addNewProduct(Product product) {
        try{
            FakeStoreDto fakeStoreRequestDto = getFakeStoreDto(product);
            FakeStoreDto fakeStoreResponseDto = fakeStoreClient.addNewProduct(fakeStoreRequestDto);
            return getProduct(fakeStoreResponseDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product updateProduct(int productId, Product product) {
        try{
            FakeStoreDto fakeStoreRequestDto = getFakeStoreDto(product);
            FakeStoreDto fakeStoreResponseDto = fakeStoreClient.updateProduct(productId, fakeStoreRequestDto);
            return getProduct(fakeStoreResponseDto);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Product getProduct(FakeStoreDto fakeStoreResponse) {
        Product product = new Product();
        product.setPrice(fakeStoreResponse.getPrice());
        product.setDescription(fakeStoreResponse.getDescription());
        product.setImageUrl(fakeStoreResponse.getImage());
        Categories categories = new Categories();
        categories.setDescription(fakeStoreResponse.getDescription());
        product.setCategory(categories);
        return product;
    }

    private FakeStoreDto getFakeStoreDto(Product product) {
        FakeStoreDto fakeStoreDto = new FakeStoreDto();
        fakeStoreDto.setId(product.getId());
        fakeStoreDto.setTitle(product.getTitle());
        fakeStoreDto.setPrice(product.getPrice());
        fakeStoreDto.setCategory(product.getCategory().toString());
        fakeStoreDto.setDescription(product.getDescription());
        fakeStoreDto.setImage(product.getImageUrl());
        return fakeStoreDto;
    }
}
