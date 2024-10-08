package com.backfcdev.managementsystem.service;

import com.backfcdev.managementsystem.dto.request.ProductRequest;
import com.backfcdev.managementsystem.dto.response.ProductResponse;
import com.backfcdev.managementsystem.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService extends ICRUD<Product, ProductRequest, ProductResponse, Integer> {
    Page<ProductResponse> findByArgs(final Optional<String> name, final Optional<Double> price, Pageable pageable);
    List<ProductResponse> findByStockLessThan(int amount);
    List<ProductResponse> getBestSellingProducts(int amount);
}
