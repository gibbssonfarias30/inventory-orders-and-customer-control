package com.backfcdev.managementsystem.service.impl;

import com.backfcdev.managementsystem.model.Client;
import com.backfcdev.managementsystem.repository.IClientRepository;
import com.backfcdev.managementsystem.repository.IGenericRepository;
import com.backfcdev.managementsystem.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl extends CRUDImpl<Client, Integer> implements IClientService {
    private final IClientRepository clientRepository;

    @Override
    protected IGenericRepository<Client, Integer> repository() {
        return clientRepository;
    }
}
