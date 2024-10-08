package com.backfcdev.managementsystem.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailResponse {

    private Integer id;

    @JsonBackReference
    private ProductResponse product;

    private double price;

    private int amount;

    @JsonBackReference
    private OrderResponse order;


    public Double getSubTotal(){
        return price * amount;
    }
}
