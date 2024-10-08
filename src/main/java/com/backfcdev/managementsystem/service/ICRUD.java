package com.backfcdev.managementsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICRUD<T, RQ, RS, ID> {

    Page<RS> findAll(Pageable pageable);
    RS findById(ID id);
    RS save(RQ request);
    RS update(ID id, RQ request);
    void delete(ID id);
}

