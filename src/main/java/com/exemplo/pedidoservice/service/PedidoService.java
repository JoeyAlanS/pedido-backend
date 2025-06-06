package com.exemplo.pedidoservice.service;

import com.exemplo.pedidoservice.client.ClienteClient;
import com.exemplo.pedidoservice.dto.ClienteNomeDTO;
import com.exemplo.pedidoservice.model.Pedido;
import com.exemplo.pedidoservice.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ClienteClient clienteClient;

    public PedidoService(PedidoRepository repository, ClienteClient clienteClient) {
        this.repository = repository;
        this.clienteClient = clienteClient;
    }

    public Pedido criarPedido(Pedido pedido) {
        double total = pedido.getItens().stream()
                .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                .sum();
        pedido.setValorTotal(total);
        pedido.setStatus("NOVO");

        // Aqui está o ponto-chave!
        // Busque o nome do cliente pelo ID:
        ClienteNomeDTO cliente = clienteClient.buscarClientePorId(pedido.getClienteId());
        if (cliente != null) {
            pedido.setClienteNome(cliente.getNome());
        } else {
            pedido.setClienteNome("Desconhecido");
        }

        return repository.save(pedido);
    }



    public List<Pedido> listarPedidosDoCliente(String clienteId) {
        return repository.findByClienteId(clienteId);
    }

    public Optional<Pedido> consultarDetalhes(String pedidoId) {
        return repository.findById(pedidoId);
    }
}