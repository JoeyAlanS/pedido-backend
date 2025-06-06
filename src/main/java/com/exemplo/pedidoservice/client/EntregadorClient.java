package com.exemplo.pedidoservice.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EntregadorClient {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://entregador-service/api/entregadores/";

    public Object buscarEntregadorPorId(String entregadorId) {
        String url = BASE_URL + entregadorId;
        return restTemplate.getForObject(url, Object.class);
    }
}