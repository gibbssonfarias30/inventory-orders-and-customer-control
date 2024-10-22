package com.backfcdev.managementsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICRUD<T, R, S, ID> {

    Page<S> findAll(Pageable pageable);
    S findById(ID id);
    S save(R request);
    S update(ID id, R request);
    void delete(ID id);
}

