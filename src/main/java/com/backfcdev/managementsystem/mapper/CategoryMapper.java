package com.backfcdev.managementsystem.mapper;

import com.backfcdev.managementsystem.dto.request.CategoryRequest;

import com.backfcdev.managementsystem.dto.response.CategoryResponse;
import com.backfcdev.managementsystem.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class CategoryMapper implements IMapper<Category, CategoryRequest, CategoryResponse> {

    private final ModelMapper modelMapper;

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.typeMap(CategoryRequest.class, Category.class)
                .addMappings(mapper -> mapper.skip(Category::setId));

        modelMapper.typeMap(Category.class, CategoryResponse.class)
                .addMappings(mapper -> mapper.map(Category::getId, CategoryResponse::setId));
    }

    @Override
    public CategoryResponse convertToResponse(Category entity) {
        return modelMapper.map(entity, CategoryResponse.class);
    }

    @Override
    public Category convertToEntity(CategoryRequest request) {
        return modelMapper.map(request, Category.class);
    }

    @Override
    public Category updateEntityFromRequest(Category existingEntity, CategoryRequest request) {
        modelMapper.map(request, existingEntity);
        return existingEntity;
    }


}
