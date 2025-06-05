package com.exemplo.pedidoservice.controller;

import com.exemplo.pedidoservice.model.Pedido;
import com.exemplo.pedidoservice.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.exemplo.pedidoservice.client.RestauranteClient;
import com.exemplo.pedidoservice.dto.ItemCardapioDTO;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.3.55:3000"})
public class PedidoController {

    private final PedidoService pedidoService;
    private final RestauranteClient restauranteClient;

    public PedidoController(PedidoService pedidoService, RestauranteClient restauranteClient) {
        this.pedidoService = pedidoService;
        this.restauranteClient = restauranteClient;
    }

    // Criar novo pedido
    @PostMapping
    public Pedido criarPedido(@RequestBody Pedido pedido) {
        return pedidoService.criarPedido(pedido);
    }

    // Listar todos pedidos de um cliente
    @GetMapping("/cliente/{clienteId}")
    public List<Pedido> listarPedidosPorCliente(@PathVariable String clienteId) {
        return pedidoService.listarPedidosDoCliente(clienteId);
    }

    // Detalhes de um pedido
    @GetMapping("/{pedidoId}")
    public Pedido detalhesPedido(@PathVariable String pedidoId) {
        return pedidoService.consultarDetalhes(pedidoId).orElse(null);
    }

    // Valor total do pedido
    @GetMapping("/{pedidoId}/valor-total")
    public Double valorTotalPedido(@PathVariable String pedidoId) {
        return pedidoService.consultarDetalhes(pedidoId)
                .map(Pedido::getValorTotal)
                .orElse(0.0);
    }

    @GetMapping("/restaurante/{restauranteId}/itens")
    public List<ItemCardapioDTO> listarItensDoRestaurante(@PathVariable String restauranteId) {
        // Agora consome o microserviço real de restaurante
        return restauranteClient.listarItensCardapio(restauranteId);
    }
}