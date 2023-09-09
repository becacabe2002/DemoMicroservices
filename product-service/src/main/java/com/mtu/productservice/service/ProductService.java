package com.mtu.productservice.service;

import com.mtu.productservice.dto.ProductRequest;
import com.mtu.productservice.dto.ProductResponse;
import com.mtu.productservice.model.Product;
import com.mtu.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("New product " +product.getId() + " is saved to DB!");
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();

        return products.stream().map(Product::toProductResponse).toList();
    }

}
