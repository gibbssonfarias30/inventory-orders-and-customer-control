package com.backfcdev.managementsystem.service.impl;

import com.backfcdev.managementsystem.model.Product;
import com.backfcdev.managementsystem.repository.IGenericRepository;
import com.backfcdev.managementsystem.repository.IProductRepository;
import com.backfcdev.managementsystem.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl extends CRUDImpl<Product, Integer> implements IProductService {

    private final IProductRepository productRepository;

    @Override
    protected IGenericRepository<Product, Integer> repository() {
        return productRepository;
    }


    @Override
    public Page<Product> findByName(String name, Pageable pageable) {
        return productRepository.findByNameContainsIgnoreCase(name, pageable);
    }

    @Override
    public Page<Product> findByArgs(Optional<String> name, Optional<Double> price, Pageable pageable) {
        Specification<Product> searchProductName = (root, query, criteriaBuilder) ->
                name.map(n -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + n + "%"))
                        .orElse(criteriaBuilder.isTrue(criteriaBuilder.literal(true)));

        Specification<Product> priceLessThan = (root, query, criteriaBuilder) ->
                price.map(p -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), p))
                        .orElse(criteriaBuilder.isTrue(criteriaBuilder.literal(true)));

        return productRepository.findAll(searchProductName.and(priceLessThan), pageable);
    }

    @Override
    public List<Product> findByStockLessThan(int amount) {
        return productRepository.findByStockLessThan(amount);
    }

    @Override
    public List<Product> getBestSellingProducts(int amount) {
        return productRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Product::getSalesQuantity).reversed())
                .limit(amount)
                .toList();
    }
}
