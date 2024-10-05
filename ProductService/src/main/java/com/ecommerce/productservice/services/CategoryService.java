package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.CategoryDto;
import com.ecommerce.productservice.dtos.ProductDto;
import com.ecommerce.productservice.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryDto saveCategory(CategoryDto categoryDto);
    CategoryDto getCategoryByCategoryId(UUID id);
    List<CategoryDto> getAllCategories();
    void deleteCategoryByCategoryId(UUID id);
    CategoryDto updateCategory(UUID id, CategoryDto categoryDto);
    void deleteAllProducts();
}
