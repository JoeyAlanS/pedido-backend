package com.exemplo.pedidoservice.client;

import com.exemplo.pedidoservice.dto.PagamentoRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PagamentoClient {

    private final String PAGAMENTO_SERVICE_URL = "http://localhost:8083/api/pagamentos";
    private final RestTemplate restTemplate = new RestTemplate();

    public void solicitarPagamento(PagamentoRequestDTO pagamentoRequest) {
        restTemplate.postForObject(PAGAMENTO_SERVICE_URL, pagamentoRequest, Void.class);
    }
}