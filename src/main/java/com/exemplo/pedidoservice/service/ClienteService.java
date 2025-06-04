package com.exemplo.pedidoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClienteService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://cliente-service/api/clientes/";

    public Object buscarClientePorId(String clienteId) {
        String url = BASE_URL + clienteId;

        return restTemplate.getForObject(url, Object.class);
    }
}