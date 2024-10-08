package com.backfcdev.managementsystem.service.impl;

import com.backfcdev.managementsystem.exception.ModelNotFoundException;
import com.backfcdev.managementsystem.mapper.IMapper;
import com.backfcdev.managementsystem.repository.IGenericRepository;
import com.backfcdev.managementsystem.service.ICRUD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public abstract class CRUDImpl<T, RQ, RS, ID> implements ICRUD<T, RQ, RS, ID> {

    protected abstract IGenericRepository<T, ID> repository();
    protected abstract IMapper<T, RQ, RS> mapper();


    @Override
    public Page<RS> findAll(Pageable pageable) {
        return repository().findAll(pageable)
                .map(mapper()::convertToResponse);
    }

    @Override
    public RS findById(ID id) {
        return repository().findById(id)
                .map(mapper()::convertToResponse)
                .orElseThrow(ModelNotFoundException::new);
    }

    @Override
    public RS save(RQ request) {
        T entity = repository().save(mapper().convertToEntity(request));
        return mapper().convertToResponse(entity);
    }

    @Override
    public RS update(ID id, RQ request) {
        T existingEntity = repository().findById(id)
                .orElseThrow(ModelNotFoundException::new);
        T updatedEntity = mapper().updateEntityFromRequest(existingEntity, request);

        return mapper().convertToResponse(repository().save(updatedEntity));
    }

    @Override
    public void delete(ID id) {
        repository().findById(id)
                .orElseThrow(ModelNotFoundException::new);
        repository().deleteById(id);
    }
}
