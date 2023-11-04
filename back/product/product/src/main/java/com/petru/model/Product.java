package com.petru.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private Long id;
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String inventoryStatus;
    private String category;
    private String image;
    private Double rating;
}
