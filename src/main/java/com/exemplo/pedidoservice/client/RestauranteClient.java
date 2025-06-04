package com.exemplo.pedidoservice.client;

import com.exemplo.pedidoservice.dto.ItemCardapioDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RestauranteClient {

    private final String RESTAURANTE_SERVICE_URL = "http://localhost:8082/api/restaurantes";

    private final RestTemplate restTemplate = new RestTemplate();

    public List<ItemCardapioDTO> listarItensCardapio(String restauranteId) {
        String url = RESTAURANTE_SERVICE_URL + "/" + restauranteId + "/cardapio";
        ItemCardapioDTO[] itens = restTemplate.getForObject(url, ItemCardapioDTO[].class);
        return Arrays.asList(itens);
    }
}