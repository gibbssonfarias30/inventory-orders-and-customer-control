package com.backfcdev.managementsystem.mapper;


public interface IMapper<T, R, S> {
    S convertToResponse(T entity);
    T convertToEntity(R request);
    T updateEntityFromRequest(T existingEntity, R request);
}
