package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.dtos.ProductDto;
import com.ecommerce.productservice.services.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@Validated
public class ProductController extends BaseController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductByProductId(@PathVariable("id") UUID productId) {
        return handleRequest(() ->
                productService.getProductByProductId(productId),
                "Product ID " + productId + " not found");
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        return handleRequest(() ->
                        productService.saveProduct(productDto),
                "An error occurred while creating product");
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return handleRequest(productService::getAllProducts,
                "All products not found");
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProductByProductId(@PathVariable("id") UUID id,
                                                               @Valid @RequestBody ProductDto productDto) {
        return handleRequest(() ->
                        productService.updateProduct(id, productDto),
                "An error occurred while updating product");
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") UUID id) {
        return handleRequest(() -> {
            productService.deleteProduct(id);
            return null;
        }, "An error occurred while deleting product");
    }

    @DeleteMapping("/products")
    public ResponseEntity<Object> deleteAllProducts(){
        return handleRequest(() -> {
            productService.deleteAllProducts();
            return null;
        }, "An error occurred while deleting all products");
    }
}
