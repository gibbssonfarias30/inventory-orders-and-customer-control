package com.backfcdev.managementsystem.service;

import com.backfcdev.managementsystem.dto.request.CategoryRequest;
import com.backfcdev.managementsystem.dto.response.CategoryResponse;
import com.backfcdev.managementsystem.model.Category;


public interface ICategoryService extends ICRUD<Category, CategoryRequest, CategoryResponse, Long> {
}
