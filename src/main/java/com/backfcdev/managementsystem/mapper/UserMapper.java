package com.backfcdev.managementsystem.mapper;

import com.backfcdev.managementsystem.dto.request.OrderRequest;
import com.backfcdev.managementsystem.dto.request.UserRequest;
import com.backfcdev.managementsystem.dto.response.UserResponse;
import com.backfcdev.managementsystem.dto.response.OrderResponse;
import com.backfcdev.managementsystem.model.User;
import com.backfcdev.managementsystem.model.Order;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserMapper implements IMapper<User, UserRequest, UserResponse> {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    public void configureMappings() {
        TypeMap<UserRequest, User> typeMapRequest = modelMapper.createTypeMap(UserRequest.class, User.class);
        typeMapRequest.addMappings(mapper -> {
            mapper.skip(User::setId);
            mapper.skip(User::setOrders);
            mapper.skip(User::setRol);
        });

        TypeMap<User, UserResponse> typeMapResponse = modelMapper.createTypeMap(User.class, UserResponse.class);
        typeMapResponse.addMappings(mapper -> {
            mapper.map(User::getId, UserResponse::setId);
            mapper.skip(UserResponse::setOrders);
            mapper.map(user -> user.getRol().getName(), UserResponse::setRol);
        });
    }

    @Override
    public UserResponse convertToResponse(User entity) {
        UserResponse response = modelMapper.map(entity, UserResponse.class);
        response.setOrders(convertOrderListToOrderResponseList(entity.getOrders()));
        return response;
    }

    @Override
    public User convertToEntity(UserRequest request) {
        User user = modelMapper.map(request, User.class);
        user.setOrders(convertOrderResponseListToOrderList(request.getOrders()));
        return user;
    }

    @Override
    public User updateEntityFromRequest(User existingEntity, UserRequest request) {
        modelMapper.map(request, existingEntity);
        return existingEntity;
    }

    private List<Order> convertOrderResponseListToOrderList(List<OrderRequest> orderRequests) {
        if (orderRequests == null) {
            return Collections.emptyList();
        }
        return orderRequests.stream()
                .map(orderResponse -> modelMapper.map(orderResponse, Order.class))
                .toList();
    }

    private List<OrderResponse> convertOrderListToOrderResponseList(List<Order> orders) {
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .toList();
    }
}
