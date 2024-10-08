package com.backfcdev.managementsystem.mapper;


public interface IMapper<T, RQ, RS> {
    RS convertToResponse(T entity);
    T convertToEntity(RQ request);
    T updateEntityFromRequest(T existingEntity, RQ request);
}
