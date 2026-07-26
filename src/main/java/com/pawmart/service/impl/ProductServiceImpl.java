package com.pawmart.service.impl;

import com.pawmart.DTO.Product.ProductImageResponse;
import com.pawmart.DTO.Product.ProductRequest;
import com.pawmart.DTO.Product.ProductResponse;
import com.pawmart.entity.Category;
import com.pawmart.entity.Product;
import com.pawmart.exception.AppException;
import com.pawmart.repository.CategoryRepository;
import com.pawmart.repository.ProductRepository;
import com.pawmart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponse create(ProductRequest request) {

        if (productRepository.existsByName(request.getName())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Product already exists");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new AppException(HttpStatus.NOT_FOUND, "Category not found"));

        Product product = new Product();

        product.setCategory(category);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setBrand(request.getBrand());
        product.setWeight(request.getWeight());

        return toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new AppException(HttpStatus.NOT_FOUND, "Product not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new AppException(HttpStatus.NOT_FOUND, "Category not found"));

        product.setCategory(category);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setBrand(request.getBrand());
        product.setWeight(request.getWeight());

        return toResponse(productRepository.save(product));
    }

    @Override
    public void delete(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new AppException(HttpStatus.NOT_FOUND, "Product not found"));

        productRepository.delete(product);
    }

    @Override
    public ProductResponse getById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new AppException(HttpStatus.NOT_FOUND, "Product not found"));

        return toResponse(product);
    }

    @Override
    public List<ProductResponse> getAll() {

        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ProductResponse toResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());

        response.setCategoryId(product.getCategory().getId());
        response.setCategoryName(product.getCategory().getName());

        response.setName(product.getName());
        response.setDescription(product.getDescription());

        response.setPrice(product.getPrice());
        response.setStock(product.getStock());

        response.setBrand(product.getBrand());
        response.setWeight(product.getWeight());

        response.setStatus(product.getStatus());

        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        response.setImages(
                product.getImages()
                        .stream()
                        .map(image -> {
                            ProductImageResponse imageResponse = new ProductImageResponse();

                            imageResponse.setId(image.getId());
                            imageResponse.setImageUrl(image.getImageUrl());
                            imageResponse.setIsThumbnail(image.getIsThumbnail());

                            return imageResponse;
                        })
                        .toList()
        );

        return response;
    }

    @Override
    public Page<ProductResponse> getProducts(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products;

        if (keyword == null || keyword.isBlank()) {
            products = productRepository.findAll(pageable);
        } else {
            products = productRepository.findByNameContainingIgnoreCase(
                    keyword,
                    pageable
            );
        }

        return products.map(this::toResponse);
    }
}
