package com.backfcdev.managementsystem.mapper;

import com.backfcdev.managementsystem.dto.request.ProductRequest;
import com.backfcdev.managementsystem.dto.response.CategoryResponse;
import com.backfcdev.managementsystem.dto.response.ProductResponse;
import com.backfcdev.managementsystem.model.Category;
import com.backfcdev.managementsystem.model.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;


@Component
public class ProductMapper implements IMapper<Product, ProductRequest, ProductResponse> {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.typeMap(ProductRequest.class, Product.class)
                .addMappings(mapper -> {
                    mapper.skip(Product::setId);
                    mapper.skip(Product::setCategory);
                });

        modelMapper.typeMap(Product.class, ProductResponse.class).addMappings( mapper -> {
            mapper.map(Product::getId, ProductResponse::setId);
            mapper.map(product -> product.getCategory().getName(), ProductResponse::setCategoryName);
        });

    }

    @Override
    public ProductResponse convertToResponse(Product entity) {
        return modelMapper.map(entity, ProductResponse.class);
    }

    @Override
    public Product convertToEntity(ProductRequest request) {
        return modelMapper.map(request, Product.class);
    }

    @Override
    public Product updateEntityFromRequest(Product existingEntity, ProductRequest request) {
        modelMapper.map(request, existingEntity);
        return existingEntity;
    }
}
