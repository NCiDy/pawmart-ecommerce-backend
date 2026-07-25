package com.pawmart.service.impl;

import com.pawmart.DTO.Category.CategoryRequest;
import com.pawmart.DTO.Category.CategoryResponse;
import com.pawmart.entity.Category;
import com.pawmart.exception.AppException;
import com.pawmart.repository.CategoryRepository;
import com.pawmart.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse create(CategoryRequest request){
        if (categoryRepository.existsByName(request.getName())){
            throw new AppException(HttpStatus.CONFLICT,"Category already exists");
        }

        Category category =  new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImage(request.getImage());
        category.setStatus("ACTIVE");

        return toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new AppException(HttpStatus.NOT_FOUND,"Category not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImage(request.getImage());

        return toResponse(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryResponse> getAll() {

        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse getById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return toResponse(category);
    }

    private CategoryResponse toResponse(Category category){

        CategoryResponse response = new CategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setImage(category.getImage());
        response.setStatus(category.getStatus());

        return response;
    }


}
