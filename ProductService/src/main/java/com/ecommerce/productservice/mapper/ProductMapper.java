package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dtos.ProductDto;
import com.ecommerce.productservice.models.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    Product productDtoToProduct(ProductDto productDto);
    ProductDto productToProductDto(Product product);
}
