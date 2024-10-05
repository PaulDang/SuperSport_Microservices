package com.ecommerce.productservice.services.impl;

import com.ecommerce.productservice.dtos.CategoryDto;
import com.ecommerce.productservice.mapper.CategoryMapper;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.repositories.CategoryRepository;
import com.ecommerce.productservice.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        ValidateCategoryDto(categoryDto);
        Category newCategory = categoryMapper.categoryDtoToCategory(categoryDto);
        CategoryDto savedCategory = categoryMapper
                .categoryToCategoryDto(categoryRepository.save(newCategory));
        log.info("Category with ID {} successfully created", savedCategory.getCategoryId());

        return savedCategory;
    }

    private void ValidateCategoryDto(CategoryDto categoryDto){
        Objects.requireNonNull(categoryDto, "Category cannot be null");
        if (ObjectUtils.isEmpty(categoryDto.getCategoryTitle())){
            log.error("Category title is empty");
            throw new IllegalArgumentException("Category title must not be empty");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryByCategoryId(UUID categoryId) {
        Objects.requireNonNull(categoryId, "Category ID cannot be null");
        return Optional
                .ofNullable(categoryRepository.findCategoryByCategoryId(categoryId))
                .map(categoryMapper::categoryToCategoryDto)
                .orElseThrow(() ->{
                    log.error("Category with ID {} not found", categoryId);
                    return new IllegalArgumentException("Category with ID " + categoryId + " not found");
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        log.info("Fetching all categories");
        List<Category> categories = categoryRepository.findAll();
        log.info(categories.isEmpty() ? "No categories found" : "Fetched {} categories", categories.size());
        return categories
                .stream()
                .map(categoryMapper::categoryToCategoryDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteCategoryByCategoryId(UUID categoryId) {
        Objects.requireNonNull(categoryId, "Category ID cannot be null");
        Category oldCategory = categoryRepository.findCategoryByCategoryId(categoryId);
        if(Optional.ofNullable(oldCategory).isPresent()) {
            log.info("Deleting Category with ID {}", categoryId);
            categoryRepository.deleteCategoryByCategoryId(oldCategory.getCategoryId());
            log.info("Category with ID {} successfully deleted", categoryId);
        }
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(UUID categoryId, CategoryDto categoryDto) {
        ValidateCategoryDto(categoryDto);
        Objects.requireNonNull(categoryId, "Category ID cannot be null");
        Category oldCategory = categoryRepository.findCategoryByCategoryId(categoryId);
        if(Optional.ofNullable(oldCategory).isEmpty()) {
            log.error("Category with ID {} not found", categoryId);
            throw new IllegalArgumentException("Category with ID " + categoryId + " not found");
        }
        log.info("Updating Category with ID {}", categoryId);
        updateCategoryFields(oldCategory, categoryDto);
        CategoryDto updatedCategory = categoryMapper
                .categoryToCategoryDto(categoryRepository.save(oldCategory));
        log.info("Category with ID {} successfully updated", categoryId);

        return updatedCategory;
    }

    @Override
    @Transactional
    public void deleteAllProducts() {
        List<Category> categories = categoryRepository.findAll();
        log.info("Deleting all {} categories", categories.size());
        categoryRepository.deleteAll();
        log.info("Deleted all categories");
    }

    private void updateCategoryFields(Category oldCategory, CategoryDto categoryDto) {
        Optional.ofNullable(categoryDto.getCategoryTitle()).ifPresent(oldCategory::setCategoryTitle);
        Optional.ofNullable(categoryDto.getImageUrl()).ifPresent(oldCategory::setImageUrl);
        oldCategory.setUploadAt(new Date());
    }
}