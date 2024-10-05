package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.dtos.CategoryDto;
import com.ecommerce.productservice.services.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@Validated
public class CategoryController extends BaseController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return handleRequest(categoryService::getAllCategories,
                "All categories not found");
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") UUID categoryId) {
        return handleRequest(() ->
                categoryService.getCategoryByCategoryId(categoryId),
                "Category ID" + categoryId + " not found");
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return handleRequest(() ->
                categoryService.saveCategory(categoryDto),
                "An error occurred while creating category");
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> updateCategoryByCategoryId(@PathVariable("id") UUID id,
                                                                  @Valid @RequestBody CategoryDto categoryDto){
        return handleRequest(() ->
                categoryService.updateCategory(id, categoryDto),
                "An error occurred while updating category");
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Object> deleteCategoryById(@PathVariable("id") UUID categoryId) {
        return handleRequest(() -> {
            categoryService.deleteCategoryByCategoryId(categoryId);
            return null;
        }, "An error occurred while deleting category");
    }

    @DeleteMapping("/categories")
    public ResponseEntity<Object> deleteAllCategories() {
        return handleRequest(() -> {
            categoryService.deleteAllProducts();
            return null;
        }, "An error occurred while deleting all categories");
    }
}
