package com.backfcdev.managementsystem.service.impl;

import com.backfcdev.managementsystem.dto.request.CategoryRequest;
import com.backfcdev.managementsystem.dto.response.CategoryResponse;
import com.backfcdev.managementsystem.mapper.CategoryMapper;
import com.backfcdev.managementsystem.mapper.IMapper;
import com.backfcdev.managementsystem.model.Category;
import com.backfcdev.managementsystem.repository.ICategoryRepository;
import com.backfcdev.managementsystem.repository.IGenericRepository;
import com.backfcdev.managementsystem.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl extends CRUDImpl<Category, CategoryRequest, CategoryResponse, Integer> implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    protected IGenericRepository<Category, Integer> repository() {
        return categoryRepository;
    }

    @Override
    protected IMapper<Category, CategoryRequest, CategoryResponse> mapper() {
        return categoryMapper;
    }

}
