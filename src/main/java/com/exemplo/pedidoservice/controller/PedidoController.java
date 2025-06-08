package com.exemplo.pedidoservice.controller;

import com.exemplo.pedidoservice.client.ClienteClient;
import com.exemplo.pedidoservice.client.EntregadorClient;
import com.exemplo.pedidoservice.dto.StatusEntregadorDTO;
import com.exemplo.pedidoservice.model.Pedido;
import com.exemplo.pedidoservice.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    private final EntregadorClient entregadorClient;


    public PedidoController(PedidoService pedidoService, RestauranteClient restauranteClient, ClienteClient clienteClient, EntregadorClient entregadorClient) {
        this.pedidoService = pedidoService;
        this.restauranteClient = restauranteClient;
        this.clienteClient = clienteClient;
        this.entregadorClient = entregadorClient;

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

    @GetMapping("todos-pedidos")
    public List<Pedido> listarTodosPedidos() {
        return pedidoService.listarTodosPedidos();
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

    @PutMapping("/{pedidoId}/entregador/{entregadorId}")
    public ResponseEntity<Pedido> associarEntregadorAoPedido(
            @PathVariable String pedidoId,
            @PathVariable String entregadorId) {
        Optional<Pedido> optional = pedidoService.consultarDetalhes(pedidoId);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();
        Pedido pedido = optional.get();
        pedido.setEntregadorId(entregadorId);
        pedidoService.salvarPedido(pedido);
        return ResponseEntity.ok(pedido);
    }

    // Buscar status e nome do entregador vinculado ao pedido via outro microserviço (Railway)
    @GetMapping("/{pedidoId}/entregador-info")
    public ResponseEntity<StatusEntregadorDTO> buscarEntregadorInfo(@PathVariable String pedidoId) {
        Optional<Pedido> optional = pedidoService.consultarDetalhes(pedidoId);
        if (optional.isEmpty() || optional.get().getEntregadorId() == null) return ResponseEntity.notFound().build();
        String entregadorId = optional.get().getEntregadorId();
        StatusEntregadorDTO dto = entregadorClient.buscarEntregadorPorId(entregadorId);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }


}