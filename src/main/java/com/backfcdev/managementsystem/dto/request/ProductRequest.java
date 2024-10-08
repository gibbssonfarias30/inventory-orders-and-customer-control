package com.backfcdev.managementsystem.dto.request;

import com.backfcdev.managementsystem.dto.response.CategoryResponse;
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

    private Integer categoryId;
}
