package com.ecommerce.productservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private int categoryId;

    @Column(name = "category_title")
    private String categoryTitle;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadAt;
}
