package com.ecommerce.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ProductDto {
    private int productId;
    private String productTitle;
    private String imageUrl;
    private String sku;
    private String priceUnit;
    private int quantity;
    private Date createdAt;
    private Date uploadAt;
}
