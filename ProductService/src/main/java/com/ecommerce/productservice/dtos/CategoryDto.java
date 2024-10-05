package com.ecommerce.productservice.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
public class CategoryDto {

    private UUID categoryId;
    @NotNull(message = "Category Title cannot be null")
    private String categoryTitle;
    private String imageUrl;
    private Date createdAt;
    private Date uploadAt;
}
