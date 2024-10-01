package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dtos.CategoryDto;
import com.ecommerce.productservice.models.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

    Category categoryDtoToCategory(CategoryDto category);
    CategoryDto categoryToCategoryDto(Category category);
}
