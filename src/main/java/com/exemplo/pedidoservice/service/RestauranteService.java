package com.exemplo.pedidoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestauranteService{

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://restaurante-service/api/restaurantes/";

    public Object buscarRestaurantePorId(String restauranteId) {
        String url = BASE_URL + restauranteId;

        return restTemplate.getForObject(url, Object.class);
    }
}