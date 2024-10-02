package com.ecommerce.productservice.repositories;

import com.ecommerce.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Product findProductByProductId(UUID productId);
    void deleteByProductId(UUID productId);
}
