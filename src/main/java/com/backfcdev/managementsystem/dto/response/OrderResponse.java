package com.backfcdev.managementsystem.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {

    private Long id;

    @JsonBackReference
    private UserResponse userResponse;

    @JsonManagedReference
    private List<OrderDetailResponse> orderDetails;

    public Double getTotal(){
        return  orderDetails.stream()
                .mapToDouble(OrderDetailResponse::getSubTotal)
                .sum();
    }
}
