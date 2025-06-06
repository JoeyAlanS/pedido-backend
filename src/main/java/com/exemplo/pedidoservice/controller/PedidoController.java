package com.exemplo.pedidoservice.controller;

import com.exemplo.pedidoservice.client.ClienteClient;
import com.exemplo.pedidoservice.model.Pedido;
import com.exemplo.pedidoservice.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.exemplo.pedidoservice.client.RestauranteClient;
import com.exemplo.pedidoservice.dto.ItemCardapioDTO;
import com.exemplo.pedidoservice.dto.ClienteNomeDTO;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.3.55:3000"})
public class PedidoController {

    private final PedidoService pedidoService;
    private final RestauranteClient restauranteClient;
    private final ClienteClient clienteClient;

    public PedidoController(PedidoService pedidoService, RestauranteClient restauranteClient, ClienteClient clienteClient) {
        this.pedidoService = pedidoService;
        this.restauranteClient = restauranteClient;
        this.clienteClient = clienteClient;
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

    // Listagem dos itens do cardápio via restauranteClient
    @GetMapping("/itensCardapio")
    public List<ItemCardapioDTO> listarItensDoCardapio() {
        return restauranteClient.listarItensCardapio();
    }

    // Buscar apenas o nome do cliente pelo ID
    @GetMapping("/cliente/{clienteId}/nome")
    public ClienteNomeDTO buscarNomeCliente(@PathVariable String clienteId) {
        return clienteClient.buscarClientePorId(clienteId);
    }
}