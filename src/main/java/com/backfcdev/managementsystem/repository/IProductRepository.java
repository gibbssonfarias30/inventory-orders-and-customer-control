package com.backfcdev.managementsystem.repository;

import com.backfcdev.managementsystem.model.Product;

import java.util.List;

public interface IProductRepository extends IGenericRepository<Product, Long> {
    List<Product> findByStockLessThan(int amount);
}
