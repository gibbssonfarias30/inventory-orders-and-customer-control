package com.backfcdev.managementsystem.service.impl;

import com.backfcdev.managementsystem.dto.request.OrderRequest;
import com.backfcdev.managementsystem.dto.response.OrderResponse;
import com.backfcdev.managementsystem.exception.InsufficientStockException;
import com.backfcdev.managementsystem.exception.ModelNotFoundException;
import com.backfcdev.managementsystem.mapper.IMapper;
import com.backfcdev.managementsystem.mapper.OrderMapper;
import com.backfcdev.managementsystem.model.User;
import com.backfcdev.managementsystem.model.Order;
import com.backfcdev.managementsystem.model.OrderDetail;
import com.backfcdev.managementsystem.model.Product;
import com.backfcdev.managementsystem.repository.IUserRepository;
import com.backfcdev.managementsystem.repository.IGenericRepository;
import com.backfcdev.managementsystem.repository.IOrderRepository;
import com.backfcdev.managementsystem.repository.IProductRepository;
import com.backfcdev.managementsystem.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl extends CRUDImpl<Order, OrderRequest, OrderResponse, Long> implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IProductRepository productRepository;
    private final IUserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    protected IGenericRepository<Order, Long> repository() {
        return orderRepository;
    }

    @Override
    protected IMapper<Order, OrderRequest, OrderResponse> mapper() {
        return orderMapper;
    }

    @Transactional
    @Override
    public OrderResponse save(OrderRequest request) {
        Order order = orderMapper.convertToEntity(request);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(ModelNotFoundException::new);
        order.setUser(user);

        List<OrderDetail> orderDetails = request.getOrderDetails().stream()
                .map(detailRequest -> {
                    Product product = productRepository.findById(detailRequest.getProductId())
                            .orElseThrow(ModelNotFoundException::new);

                    int stock = product.getStock();
                    int amountOrder = detailRequest.getAmount();

                    if (amountOrder > stock) {
                        throw new InsufficientStockException();
                    }

                    int newStock = stock - amountOrder;
                    product.setStock(newStock);
                    product.setSalesQuantity(product.getSalesQuantity() + amountOrder);
                    productRepository.save(product);

                    return OrderDetail.builder()
                            .product(product)
                            .price(detailRequest.getPrice())
                            .amount(amountOrder)
                            .order(order)
                            .build();
                })
                .toList();

        order.setOrderDetails(orderDetails);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.convertToResponse(savedOrder);
    }


    @Transactional
    @Override
    public OrderResponse update(Long id, OrderRequest request) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(ModelNotFoundException::new);

        existingOrder.getOrderDetails().forEach(existingDetail -> {
            Product product = existingDetail.getProduct();
            int revertedStock = product.getStock() + existingDetail.getAmount();
            product.setStock(revertedStock);
            product.setSalesQuantity(product.getSalesQuantity() - existingDetail.getAmount());
            productRepository.save(product);
        });

        existingOrder.getOrderDetails().clear();

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(ModelNotFoundException::new);
        existingOrder.setUser(user);

        request.getOrderDetails().forEach(detailRequest -> {
            Product product = productRepository.findById(detailRequest.getProductId())
                    .orElseThrow(ModelNotFoundException::new);

            int stock = product.getStock();
            int amountOrder = detailRequest.getAmount();

            if (amountOrder > stock) {
                throw new InsufficientStockException();
            }

            product.setStock(stock - amountOrder);
            product.setSalesQuantity(product.getSalesQuantity() + amountOrder);
            productRepository.save(product);

            OrderDetail orderDetail = OrderDetail.builder()
                    .product(product)
                    .price(detailRequest.getPrice())
                    .amount(amountOrder)
                    .build();

            existingOrder.addOrderDetail(orderDetail);
        });
        Order updatedOrder = orderRepository.save(existingOrder);

        return orderMapper.convertToResponse(updatedOrder);
    }
}
