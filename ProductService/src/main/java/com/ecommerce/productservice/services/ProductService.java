package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.ProductDto;
import com.ecommerce.productservice.models.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product saveProduct(ProductDto productDto);

    Product getProductByProductId(UUID productId);

    Product updateProduct(UUID productId, ProductDto productdto);

    void deleteProduct(UUID productId);

    List<Product> getAllProducts();
}
