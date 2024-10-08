package com.backfcdev.managementsystem.service;

import com.backfcdev.managementsystem.dto.request.ClientRequest;
import com.backfcdev.managementsystem.dto.response.ClientResponse;
import com.backfcdev.managementsystem.model.Client;

public interface IClientService extends ICRUD<Client, ClientRequest, ClientResponse, Integer> {
}
