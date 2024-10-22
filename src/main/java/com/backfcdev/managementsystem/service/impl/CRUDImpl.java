package com.backfcdev.managementsystem.service.impl;

import com.backfcdev.managementsystem.exception.ModelNotFoundException;
import com.backfcdev.managementsystem.mapper.IMapper;
import com.backfcdev.managementsystem.repository.IGenericRepository;
import com.backfcdev.managementsystem.service.ICRUD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public abstract class CRUDImpl<T, R, S, ID> implements ICRUD<T, R, S, ID> {

    protected abstract IGenericRepository<T, ID> repository();
    protected abstract IMapper<T, R, S> mapper();


    @Override
    public Page<S> findAll(Pageable pageable) {
        return repository().findAll(pageable)
                .map(mapper()::convertToResponse);
    }

    @Override
    public S findById(ID id) {
        return repository().findById(id)
                .map(mapper()::convertToResponse)
                .orElseThrow(ModelNotFoundException::new);
    }

    @Override
    public S save(R request) {
        T entity = repository().save(mapper().convertToEntity(request));
        return mapper().convertToResponse(entity);
    }

    @Override
    public S update(ID id, R request) {
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
