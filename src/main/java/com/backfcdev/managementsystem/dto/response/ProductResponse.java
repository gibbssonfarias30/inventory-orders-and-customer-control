package com.backfcdev.managementsystem.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Long id;

    private String name;

    private double price;

    private int stock;

    private String imageUrl;

    private String categoryName;

    private int salesQuantity;
}
