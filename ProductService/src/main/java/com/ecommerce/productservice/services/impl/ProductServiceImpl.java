package com.ecommerce.productservice.services.impl;

import com.ecommerce.productservice.dtos.ProductDto;
import com.ecommerce.productservice.mapper.ProductMapper;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.repositories.ProductRepository;
import com.ecommerce.productservice.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional
    public ProductDto saveProduct(ProductDto productDto) {
        validateProductDto(productDto);

        if (ObjectUtils.isEmpty(productDto.getProductTitle()) || productDto.getPriceUnit() == null) {
            log.error("Product title or price unit is empty");
            throw new IllegalArgumentException("Product title and price unit must not be empty");
        }

        Product newProduct = productMapper.productDtoToProduct(productDto);
        ProductDto savedProduct = productMapper
                .productToProductDto(productRepository.save(newProduct));

        log.info("Product with ID {} successfully created", savedProduct.getProductId());

        return savedProduct;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductByProductId(UUID productId) {
        Objects.requireNonNull(productId, "Product ID cannot be null");
        return Optional
                .ofNullable(productRepository.findProductByProductId(productId))
                .map(productMapper::productToProductDto)
                .orElseThrow(() -> {
                    log.error("Product ID {} does not exist", productId);
                    return new IllegalArgumentException("Product ID " + productId + " does not exist");
                });
    }

    @Override
    @Transactional
    public ProductDto updateProduct(UUID productId, ProductDto productDto) {
        Objects.requireNonNull(productId, "Product ID cannot be null");
        validateProductDto(productDto);

        Product oldProduct = productRepository.findProductByProductId(productId);
        if (Optional.ofNullable(oldProduct).isEmpty()) {
            log.error("Product ID {} does not exist", productId);
            throw new IllegalArgumentException("Product ID " + productId + " does not exist");
        }

        log.info("Updating product with ID {}", productId);
        updateProductFields(oldProduct, productDto);

        ProductDto updatedProduct = productMapper
                .productToProductDto(productRepository.save(oldProduct));
        log.info("Product with ID {} successfully updated", productId);

        return updatedProduct;
    }

    private void updateProductFields(Product oldProduct, ProductDto productDto) {
        Optional.ofNullable(productDto.getProductTitle()).ifPresent(oldProduct::setProductTitle);
        Optional.ofNullable(productDto.getPriceUnit()).ifPresent(oldProduct::setPriceUnit);
        Optional.ofNullable(productDto.getSku()).ifPresent(oldProduct::setSku);
        Optional.ofNullable(productDto.getImageUrl()).ifPresent(oldProduct::setImageUrl);
        oldProduct.setUploadAt(new Date());
        oldProduct.setQuantity(productDto.getQuantity());
    }

    private void validateProductDto(ProductDto productDto) {
        Objects.requireNonNull(productDto, "Product cannot be null");

        if (ObjectUtils.isEmpty(productDto.getProductTitle()) || productDto.getPriceUnit() == null) {
            log.error("Product title or price unit is empty");
            throw new IllegalArgumentException("Product title and price unit must not be empty");
        }

        if (productDto.getPriceUnit() < 0) {
            log.error("Price unit must be non-negative");
            throw new IllegalArgumentException("Price unit must be non-negative");
        }

        if (productDto.getQuantity() < 0) {
            log.error("Quantity must be non-negative");
            throw new IllegalArgumentException("Quantity must be non-negative");
        }
    }

    @Override
    @Transactional
    public void deleteProduct(UUID productId) {
        Objects.requireNonNull(productId, "Product ID cannot be null");
        Product oldProduct = productRepository.findProductByProductId(productId);
        if (Optional.ofNullable(oldProduct).isPresent()) {
            log.info("Deleting product with ID {}", oldProduct.getProductId());
            productRepository.deleteProductByProductId(oldProduct.getProductId());
            log.info("Product with ID {} successfully deleted", oldProduct.getProductId());
        }
    }

    @Override
    @Transactional
    public void deleteAllProducts() {
        List<Product> products = productRepository.findAll();
        log.info("Deleting all {} products", products.size());
        productRepository.deleteAll();
        log.info("Deleted all products");
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        log.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        log.info(products.isEmpty() ? "No products found" : "Fetched {} products", products.size());
        return products.stream()
                .map(productMapper::productToProductDto)
                .toList();
    }
}