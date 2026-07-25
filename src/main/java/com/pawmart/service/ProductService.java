package com.pawmart.service;

import com.pawmart.DTO.Product.ProductRequest;
import com.pawmart.DTO.Product.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);

    ProductResponse getById(Long id);

    List<ProductResponse> getAll();

    Page<ProductResponse> getProducts(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction);
}
