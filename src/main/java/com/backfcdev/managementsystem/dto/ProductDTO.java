package com.backfcdev.managementsystem.dto;

import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO {
    private Integer id;

    private String name;

    private double price;

    private int stock;
    private String imageUrl;

    private CategoryDTO category;

    private int salesQuantity;
}
