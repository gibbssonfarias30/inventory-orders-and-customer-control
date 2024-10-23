package com.backfcdev.managementsystem.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    private String name;

    private double price;

    private int stock;

    private String imageUrl;

    private int salesQuantity;

    private Long categoryId;
}
