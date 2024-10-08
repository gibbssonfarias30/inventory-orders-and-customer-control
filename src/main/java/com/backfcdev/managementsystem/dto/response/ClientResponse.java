package com.backfcdev.managementsystem.dto.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ClientResponse {
    private Integer id;

    private String name;

    private String lastName;

    private String email;

    private String address;

    private String phone;

    @JsonManagedReference
    private List<OrderResponse> orders;
}
