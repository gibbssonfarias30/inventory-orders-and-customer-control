package com.backfcdev.managementsystem.service.impl;

import com.backfcdev.managementsystem.dto.request.ClientRequest;
import com.backfcdev.managementsystem.dto.response.ClientResponse;
import com.backfcdev.managementsystem.mapper.ClientMapper;
import com.backfcdev.managementsystem.mapper.IMapper;
import com.backfcdev.managementsystem.model.Client;
import com.backfcdev.managementsystem.repository.IClientRepository;
import com.backfcdev.managementsystem.repository.IGenericRepository;
import com.backfcdev.managementsystem.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl extends CRUDImpl<Client, ClientRequest, ClientResponse, Integer> implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper clientMapper;


    @Override
    protected IGenericRepository<Client, Integer> repository() {
        return clientRepository;
    }

    @Override
    protected IMapper<Client, ClientRequest, ClientResponse> mapper() {
        return clientMapper;
    }
}
