package com.backfcdev.managementsystem.service;

import com.backfcdev.managementsystem.dto.request.OrderRequest;
import com.backfcdev.managementsystem.dto.response.OrderResponse;
import com.backfcdev.managementsystem.model.Order;

public interface IOrderService extends ICRUD<Order, OrderRequest, OrderResponse, Integer>{
}
