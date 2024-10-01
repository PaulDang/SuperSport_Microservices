package com.ecommerce.productservice.services.impl;

import com.ecommerce.productservice.dtos.ProductDto;
import com.ecommerce.productservice.mapper.ProductMapper;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.repositories.ProductRepository;
import com.ecommerce.productservice.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional
    public Product saveProduct(ProductDto productDto) {

        Optional.ofNullable(productDto)
                .orElseThrow(() -> {
                    log.error("Product is null");
                    return new IllegalArgumentException("Product cannot be null");
                });

        if (ObjectUtils.isEmpty(productDto.getProductTitle()) || productDto.getPriceUnit() == null) {
            log.info("Product title and price unit should not be empty");
            throw new IllegalArgumentException("Product title and price unit must not be empty");
        }
        Product newProduct = productMapper.productDtoToProduct(productDto);
        Product savedProduct = productRepository.save(newProduct);
        log.info("Product with ID {} successfully created", savedProduct.getProductId());

        return savedProduct;
    }

    @Override
    public Product getProductByProductId(UUID productId) {
        if(productId == null) {
            return null;
        }
        return Optional.ofNullable(productRepository.findProductByProductId(productId))
                .orElseThrow(() -> {
                    log.error("Product ID {} does not exist", productId);
                    return new IllegalArgumentException("Product ID " + productId + " does not exist");
                });
    }

    @Override
    @Transactional
    public Product updateProduct(UUID productId, ProductDto productDto) {
        Product oldProduct = Optional.ofNullable(getProductByProductId(productId))
                .orElseThrow(() -> {
                    log.error("Product ID {} does not exist", productId);
                    return new IllegalArgumentException("Product ID " + productId + " does not exist");
                });
        Optional.ofNullable(productDto)
                .orElseThrow(() -> {
                    log.error("Product is null");
                    return new IllegalArgumentException("Product does need to be updated");
                });
        if (ObjectUtils.isEmpty(productDto.getProductTitle()) || productDto.getPriceUnit() == null) {
            log.info("Product title and price unit should not be empty");
            throw new IllegalArgumentException("Product title and price unit must not be empty");
        }

        log.info("Updating product with ID {}", productId);
        updateProductFields(oldProduct, productDto);

        Product updatedProduct = productRepository.save(oldProduct);
        log.info("Product with ID {} successfully updated", productId);

        return updatedProduct;
    }

    private void updateProductFields(Product oldProduct, ProductDto productDto) {
        if (productDto.getProductTitle() != null) {
            oldProduct.setProductTitle(productDto.getProductTitle());
        }
        if (productDto.getPriceUnit() != null) {
            oldProduct.setPriceUnit(productDto.getPriceUnit());
        }
        if (productDto.getSku() != null) {
            oldProduct.setSku(productDto.getSku());
        }
        if (productDto.getImageUrl() != null) {
            oldProduct.setImageUrl(productDto.getImageUrl());
        }
        if (productDto.getUploadAt() != null) {
            oldProduct.setUploadAt(productDto.getUploadAt());
        }
        oldProduct.setQuantity(productDto.getQuantity());
    }

    @Override
    @Transactional
    public void deleteProduct(UUID productId) {
        Product oldProduct = getProductByProductId(productId);

        log.info("Deleting product with ID {}", oldProduct.getProductId());
        productRepository.deleteByProductId(oldProduct.getProductId());
        log.info("Product with ID {} successfully deleted", oldProduct.getProductId());
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Start Fetching all products");
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            log.warn("No products found");
        } else {
            log.info("Fetched {} products", products.size());
        }
        return products;
    }
}
