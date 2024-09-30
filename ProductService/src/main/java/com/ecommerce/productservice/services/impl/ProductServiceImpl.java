package com.ecommerce.productservice.services.impl;

import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.repositories.ProductRepository;
import com.ecommerce.productservice.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Product saveProduct(Product product) {
        Optional.ofNullable(product)
                .orElseThrow(() -> {
                    log.error("Product is null");
                    return new IllegalArgumentException("Product cannot be null");
                });

        if (product.getProductId() != null) {
            log.info("Product id should be null when creating a new product");
            throw new IllegalArgumentException("Product id should be null when creating a new product");
        }

        if (ObjectUtils.isEmpty(product.getProductTitle()) || product.getPriceUnit() == null) {
            log.info("Product title and price unit should not be empty");
            throw new IllegalArgumentException("Product title and price unit must not be empty");
        }
        return productRepository.save(product);
    }

    @Override
    public Product getProductByProductId(UUID productId) {
        if(productId == null) {
            return null;
        }
        return productRepository.findProductByProductId(productId);
    }

    @Override
    public Product updateProduct(UUID productId, Product product) {
        Product oldProduct = getProductByProductId(productId);

        return null;
    }

    @Override
    public void deleteProduct(UUID productId) {

    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }
}
