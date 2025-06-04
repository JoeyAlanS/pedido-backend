package com.exemplo.pedidoservice.service;

import com.exemplo.pedidoservice.client.PagamentoClient;
import com.exemplo.pedidoservice.dto.PagamentoRequestDTO;
import com.exemplo.pedidoservice.model.Pedido;
import com.exemplo.pedidoservice.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final PagamentoClient pagamentoClient;


    public PedidoService(PedidoRepository repository,PagamentoClient pagamentoClient) {
        this.repository = repository;
        this.pagamentoClient = pagamentoClient;

    }

    public Pedido criarPedido(Pedido pedido) {
        double total = pedido.getItens().stream()
                .mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade())
                .sum();
        pedido.setValorTotal(total);
        pedido.setStatus("NOVO");
        Pedido pedidoSalvo = repository.save(pedido);

        PagamentoRequestDTO pagamento = new PagamentoRequestDTO();
        pagamento.setPedidoId(pedidoSalvo.getId());
        pagamento.setValor(pedidoSalvo.getValorTotal());
        pagamentoClient.solicitarPagamento(pagamento);

        return pedidoSalvo;
    }


    public List<Pedido> listarPedidosDoCliente(String clienteId) {
        return repository.findByClienteId(clienteId);
    }

    public Optional<Pedido> consultarDetalhes(String pedidoId) {
        return repository.findById(pedidoId);
    }
}