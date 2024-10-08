package com.backfcdev.managementsystem.mapper;

import com.backfcdev.managementsystem.dto.request.ClientRequest;
import com.backfcdev.managementsystem.dto.response.ClientResponse;
import com.backfcdev.managementsystem.dto.response.OrderResponse;
import com.backfcdev.managementsystem.model.Client;
import com.backfcdev.managementsystem.model.Order;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper implements IMapper<Client, ClientRequest, ClientResponse> {

    private final ModelMapper modelMapper;

    public ClientMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }


    public void configureMappings() {
        TypeMap<ClientRequest, Client> typeMapRequest = modelMapper.createTypeMap(ClientRequest.class, Client.class);
        typeMapRequest.addMappings(mapper -> {
            mapper.skip(Client::setId);
            mapper.skip(Client::setOrders);
        });

        TypeMap<Client, ClientResponse> typeMapResponse = modelMapper.createTypeMap(Client.class, ClientResponse.class);
        typeMapResponse.addMappings(mapper -> {
            mapper.map(Client::getId, ClientResponse::setId);
            mapper.skip(ClientResponse::setOrders);
        });
    }

    @Override
    public ClientResponse convertToResponse(Client entity) {
        ClientResponse response = modelMapper.map(entity, ClientResponse.class);
        response.setOrders(convertOrderListToOrderResponseList(entity.getOrders()));
        return response;
    }

    @Override
    public Client convertToEntity(ClientRequest request) {
        Client client = modelMapper.map(request, Client.class);
        client.setOrders(convertOrderResponseListToOrderList(request.getOrders()));
        return client;
    }

    @Override
    public Client updateEntityFromRequest(Client existingEntity, ClientRequest request) {
        modelMapper.map(request, existingEntity);
        return existingEntity;
    }

    private List<Order> convertOrderResponseListToOrderList(List<OrderResponse> orderResponses) {
        if (orderResponses == null) {
            return Collections.emptyList();
        }
        return orderResponses.stream()
                .map(orderResponse -> modelMapper.map(orderResponse, Order.class))
                .toList();
    }

    private List<OrderResponse> convertOrderListToOrderResponseList(List<Order> orders) {
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .toList();
    }
}
