package com.ecommerce.productservice.dtos;

import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

@Getter
@Setter
public class CategoryDto {

    private int categoryId;
    private String categoryTitle;
    private String imageUrl;
    private Date createdAt;
    private Date uploadAt;
}
