package com.exemplo.pedidoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EntregadorService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://entregador-service/api/entregadores/";

    public Object buscarEntregadorPorId(String entregadorId) {
        String url = BASE_URL + entregadorId;

        return restTemplate.getForObject(url, Object.class);
    }
}