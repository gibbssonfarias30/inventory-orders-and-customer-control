package com.backfcdev.managementsystem.service;

import com.backfcdev.managementsystem.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService extends ICRUD<Product, Integer> {
    Page<Product> findByArgs(final Optional<String> name, final Optional<Double> price, Pageable pageable);
    List<Product> findByStockLessThan(int amount);
    List<Product> getBestSellingProducts(int amount);
}
