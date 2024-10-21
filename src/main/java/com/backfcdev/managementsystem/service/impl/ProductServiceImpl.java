package com.backfcdev.managementsystem.service.impl;

import com.backfcdev.managementsystem.dto.request.ProductRequest;
import com.backfcdev.managementsystem.dto.response.ProductResponse;
import com.backfcdev.managementsystem.exception.ModelNotFoundException;
import com.backfcdev.managementsystem.mapper.IMapper;
import com.backfcdev.managementsystem.mapper.ProductMapper;
import com.backfcdev.managementsystem.model.Product;
import com.backfcdev.managementsystem.repository.ICategoryRepository;
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
public class ProductServiceImpl extends CRUDImpl<Product, ProductRequest, ProductResponse, Integer> implements IProductService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final ProductMapper productMapper;


    @Override
    protected IGenericRepository<Product, Integer> repository() {
        return productRepository;
    }

    @Override
    protected IMapper<Product, ProductRequest, ProductResponse> mapper() {
        return productMapper;
    }


    @Override
    public ProductResponse save(ProductRequest request) {
        Product productSaved = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .imageUrl(request.getImageUrl())
                .category(categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(ModelNotFoundException::new))
                .build();
        return productMapper.convertToResponse(productRepository.save(productSaved));
    }

    @Override
    public Page<ProductResponse> findByArgs(Optional<String> name, Optional<Double> price, Pageable pageable) {
        Specification<Product> searchProductName = (root, query, criteriaBuilder) ->
                name.map(n -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + n + "%"))
                        .orElse(criteriaBuilder.isTrue(criteriaBuilder.literal(true)));

        Specification<Product> priceLessThan = (root, query, criteriaBuilder) ->
                price.map(p -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), p))
                        .orElse(criteriaBuilder.isTrue(criteriaBuilder.literal(true)));

        return productRepository.findAll(searchProductName.and(priceLessThan), pageable).map(productMapper::convertToResponse);
    }

    @Override
    public List<ProductResponse> findByStockLessThan(int amount) {
        return productRepository.findByStockLessThan(amount).stream()
                .map(productMapper::convertToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getBestSellingProducts(int amount) {
        return productRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Product::getSalesQuantity).reversed())
                .limit(amount)
                .map(productMapper::convertToResponse)
                .toList();
    }
}
