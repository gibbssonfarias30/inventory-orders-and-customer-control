package com.backfcdev.managementsystem.dto.request;

import com.backfcdev.managementsystem.dto.response.OrderDetailResponse;
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
