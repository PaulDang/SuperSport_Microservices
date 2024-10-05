package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.ProductDto;
import com.ecommerce.productservice.models.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDto saveProduct(ProductDto productDto);

    ProductDto getProductByProductId(UUID productId);

    ProductDto updateProduct(UUID productId, ProductDto productdto);

    void deleteProduct(UUID productId);

    void deleteAllProducts();

    List<ProductDto> getAllProducts();
}
