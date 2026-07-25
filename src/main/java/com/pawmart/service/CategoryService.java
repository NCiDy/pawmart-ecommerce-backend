package com.pawmart.service;

import com.pawmart.DTO.Category.CategoryRequest;
import com.pawmart.DTO.Category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(Long id, CategoryRequest request);

    void delete(Long id);

    List<CategoryResponse> getAll();

    CategoryResponse getById(Long id);
}
