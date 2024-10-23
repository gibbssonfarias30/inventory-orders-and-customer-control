package com.backfcdev.managementsystem.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class UserRequest {

    private String name;

    private String lastName;

    private String email;

    private String rolId;

    private String password;

    private String address;

    private String phone;

    private List<OrderRequest> orders;
}
