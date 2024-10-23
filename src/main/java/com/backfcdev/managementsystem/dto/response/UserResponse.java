package com.backfcdev.managementsystem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String password;

    private String rol;

    private String address;

    private String phone;

    @JsonManagedReference
    private List<OrderResponse> orders;
}
