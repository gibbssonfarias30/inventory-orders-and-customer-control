package com.backfcdev.managementsystem.service.impl;

import com.backfcdev.managementsystem.model.Category;
import com.backfcdev.managementsystem.repository.ICategoryRepository;
import com.backfcdev.managementsystem.repository.IGenericRepository;
import com.backfcdev.managementsystem.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl extends CRUDImpl<Category, Integer> implements ICategoryService {
    private final ICategoryRepository categoryRepository;

    @Override
    protected IGenericRepository<Category, Integer> repository() {
        return categoryRepository;
    }
}
