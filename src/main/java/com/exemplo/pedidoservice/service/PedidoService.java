package com.exemplo.pedidoservice.service;

import com.exemplo.pedidoservice.client.ClienteClient;
import com.exemplo.pedidoservice.client.EntregadorClient;
import com.exemplo.pedidoservice.dto.ClienteNomeDTO;
import com.exemplo.pedidoservice.dto.StatusEntregadorDTO;
import com.exemplo.pedidoservice.model.Pedido;
import com.exemplo.pedidoservice.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteClient clienteClient;
    private final EntregadorClient entregadorClient;


    public PedidoService(PedidoRepository repository, ClienteClient clienteClient, EntregadorClient entregadorClient) {
        this.repository = repository;
        this.clienteClient = clienteClient;
        this.entregadorClient = entregadorClient;

    }

    public Pedido criarPedido(Pedido pedido) {
        double total = pedido.getItens().stream()
                .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                .sum();
        pedido.setValorTotal(total);

        ClienteNomeDTO cliente = clienteClient.buscarClientePorId(pedido.getClienteId());
        if (cliente != null) {
            pedido.setClienteNome(cliente.getNome());
        } else {
            pedido.setClienteNome("Desconhecido");
        }

        // Busca o status do entregador, se houver entregador
        String status = "PENDENTE";
        if (pedido.getEntregadorId() != null) {
            StatusEntregadorDTO entregadorInfo = entregadorClient.buscarEntregadorPorId(pedido.getEntregadorId());
            if (entregadorInfo != null && entregadorInfo.getStatusEntrega() != null) {
                status = entregadorInfo.getStatusEntrega();
            }
        }
        pedido.setStatus(status);

        return repository.save(pedido);
    }

    public List<Pedido> listarPedidosDoCliente(String clienteId) {
        return repository.findByClienteId(clienteId);
    }

    public Optional<Pedido> consultarDetalhes(String pedidoId) {
        return repository.findById(pedidoId);
    }

    public Pedido salvarPedido(Pedido pedido) {
        return repository.save(pedido);
    }

}