package com.ecommerce.productservice.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
public class ProductDto {
    private UUID productId;
    @NotNull(message = "Product title cannot be null")
    private String productTitle;
    @NotNull(message = "Image URL cannot be null")
    private String imageUrl;
    private String sku;
    @Positive(message = "Price unit must be greater than 0")
    private Double priceUnit;
    @Positive(message = "Quantity must be greater than 0")
    private int quantity;
    private Date createdAt;
    private Date uploadAt;
}
