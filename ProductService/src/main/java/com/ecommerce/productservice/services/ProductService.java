package com.ecommerce.productservice.services;

import com.ecommerce.productservice.models.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product saveProduct(Product product);

    Product getProductByProductId(UUID productId);

    Product updateProduct(UUID productId, Product product);

    void deleteProduct(UUID productId);

    List<Product> getAllProducts();
}
