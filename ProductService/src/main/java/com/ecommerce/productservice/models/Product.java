package com.ecommerce.productservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_title", nullable = false)
    private String productTitle;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "sku", unique = true, nullable = false)
    private String sku;

    @Column(name = "price_unit")
    private String priceUnit;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "upload_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
