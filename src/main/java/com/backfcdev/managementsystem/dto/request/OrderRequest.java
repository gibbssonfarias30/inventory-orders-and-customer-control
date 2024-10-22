package com.backfcdev.managementsystem.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class OrderRequest {

    private Integer clientId;
    private List<OrderDetailRequest> orderDetails;
}
