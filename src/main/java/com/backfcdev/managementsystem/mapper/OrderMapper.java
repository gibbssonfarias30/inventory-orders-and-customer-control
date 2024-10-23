package com.backfcdev.managementsystem.mapper;

import com.backfcdev.managementsystem.dto.request.OrderDetailRequest;
import com.backfcdev.managementsystem.dto.request.OrderRequest;
import com.backfcdev.managementsystem.dto.response.OrderDetailResponse;
import com.backfcdev.managementsystem.dto.response.OrderResponse;
import com.backfcdev.managementsystem.model.Order;
import com.backfcdev.managementsystem.model.OrderDetail;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OrderMapper implements IMapper<Order, OrderRequest, OrderResponse>{

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.typeMap(OrderRequest.class, Order.class).addMappings(mapper -> {
            mapper.skip(Order::setId);
            mapper.skip(Order::setUser);
            mapper.skip(Order::setOrderDetails);
        });

        modelMapper.typeMap(Order.class, OrderResponse.class).addMappings(mapper -> {
            mapper.map(Order::getUser, OrderResponse::setUserResponse);
            mapper.map(Order::getId, OrderResponse::setId);
            mapper.map(Order::getOrderDetails, OrderResponse::setOrderDetails);
        });

        modelMapper.typeMap(OrderDetailRequest.class, OrderDetail.class)
                .addMappings(mapper -> mapper.skip(OrderDetail::setId));
    }

    @Override
    public OrderResponse convertToResponse(Order entity) {
        OrderResponse orderResponse = modelMapper.map(entity, OrderResponse.class);
        orderResponse.setOrderDetails(convertOrderDetailListToOrderDetailResponseList(entity.getOrderDetails()));
        return orderResponse;
    }

    @Override
    public Order convertToEntity(OrderRequest request) {
        Order order = modelMapper.map(request, Order.class);

        List<OrderDetail> orderDetails = request.getOrderDetails().stream()
                .map(orderDetailRequest -> modelMapper.map(orderDetailRequest, OrderDetail.class))
                .toList();

        order.setOrderDetails(orderDetails);
        return order;
    }

    @Override
    public Order updateEntityFromRequest(Order existingEntity, OrderRequest request) {
        modelMapper.map(request, existingEntity);
        return existingEntity;
    }


    private List<OrderDetailResponse> convertOrderDetailListToOrderDetailResponseList(List<OrderDetail> orderDetails) {
        if (orderDetails == null) {
            return Collections.emptyList();
        }
        return orderDetails.stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailResponse.class))
                .toList();
    }
}
