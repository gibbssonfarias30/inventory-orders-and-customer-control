package com.backfcdev.managementsystem.dto.request;

import com.backfcdev.managementsystem.dto.response.OrderResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientRequest {

    private String name;

    private String lastName;

    private String email;

    private String address;

    private String phone;

    private List<OrderResponse> orders;
}
