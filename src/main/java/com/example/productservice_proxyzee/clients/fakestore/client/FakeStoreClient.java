package com.example.productservice_proxyzee.clients.fakestore.client;

import com.example.productservice_proxyzee.clients.fakestore.dto.FakeStoreDto;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class FakeStoreClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreDto getSingleProduct(Long productID) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        try {
            ResponseEntity<FakeStoreDto> fakeStoreResponse = restTemplate.getForEntity(
                    "https://fakestoreapi.com/products/{productID}", FakeStoreDto.class, productID);
            if (fakeStoreResponse.getBody() != null) {
                return fakeStoreResponse.getBody();
            }
            return null;
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FakeStoreDto> getAllProduct() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        List<FakeStoreDto> productList = new ArrayList<>();
        try {
            ResponseEntity<FakeStoreDto[]> fakeStoreResponse = restTemplate.getForEntity(
                    "https://fakestoreapi.com/products", FakeStoreDto[].class);
            if (fakeStoreResponse.getBody() != null) {
                FakeStoreDto[] fakeStoreDtos = fakeStoreResponse.getBody();
                for (int i = 0; i < fakeStoreDtos.length; i++) {
                    productList.add(fakeStoreDtos[i]);
                }
            }
            return productList;
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public FakeStoreDto addNewProduct(FakeStoreDto fakeStoreDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        try {
            ResponseEntity<FakeStoreDto> fakeStoreResponse =
                    restTemplate.postForEntity("https://fakestoreapi.com/products", fakeStoreDto, FakeStoreDto.class);
            if (fakeStoreResponse.getBody() != null) {
                return fakeStoreResponse.getBody();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FakeStoreDto updateProduct(int productId, FakeStoreDto fakeStoreDto) {
        try {
            ResponseEntity<FakeStoreDto> fakeStoreResponse =
                    requestForEntity(
                            HttpMethod.PATCH,
                            "https://fakestoreapi.com/products/{productId}",
                            fakeStoreDto,
                            FakeStoreDto.class,
                            productId);
            if (fakeStoreResponse.getBody() != null)
                return fakeStoreResponse.getBody();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request,
                                                   Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class
        ).build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

}
