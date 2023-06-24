package com.backfcdev.managementsystem.service.impl;

import com.backfcdev.managementsystem.exception.InsufficientStockException;
import com.backfcdev.managementsystem.exception.ModelNotFoundException;
import com.backfcdev.managementsystem.model.Order;
import com.backfcdev.managementsystem.model.Product;
import com.backfcdev.managementsystem.repository.IGenericRepository;
import com.backfcdev.managementsystem.repository.IOrderRepository;
import com.backfcdev.managementsystem.service.IOrderService;
import com.backfcdev.managementsystem.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl extends CRUDImpl<Order, Integer> implements IOrderService {
    private final IOrderRepository orderRepository;
    private final IProductService productService;

    @Override
    protected IGenericRepository<Order, Integer> repository() {
        return orderRepository;
    }

    @Override
    public Order save(Order order) {
        order.getOrderDetails()
                .forEach(orderDetail -> {
                    Product product = productService.findById(orderDetail.getProduct().getId());
                    Optional.ofNullable(product).orElseThrow(ModelNotFoundException::new);

                    int stock = product.getStock();
                    int amountOrder = orderDetail.getAmount();
                    if (amountOrder > stock) throw new InsufficientStockException();

                    int newStock = stock - amountOrder;
                    product.setStock(newStock);
                    product.setSalesQuantity(amountOrder);
                    productService.save(product);
                });

        return super.save(order);
    }
}
