package com.backfcdev.managementsystem.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailRequest {

    private Long productId;

    private double price;

    private int amount;
}
