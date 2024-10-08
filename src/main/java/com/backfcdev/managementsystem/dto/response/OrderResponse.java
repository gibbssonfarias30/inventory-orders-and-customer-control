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

    private Integer id;

    @JsonBackReference
    private ClientResponse clientResponse;

    @JsonManagedReference
    private List<OrderDetailResponse> orderDetails;

    public Double getTotal(){
        return  orderDetails.stream()
                .mapToDouble(OrderDetailResponse::getSubTotal)
                .sum();
    }
}
