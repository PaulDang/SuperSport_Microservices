package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dtos.CategoryDto;
import com.ecommerce.productservice.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category categoryDtoToCategory(CategoryDto category);
    CategoryDto categoryToCategoryDto(Category category);
}