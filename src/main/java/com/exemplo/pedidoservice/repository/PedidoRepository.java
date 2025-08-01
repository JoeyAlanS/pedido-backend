package com.exemplo.pedidoservice.repository;

import com.exemplo.pedidoservice.model.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends MongoRepository<Pedido, String> {
    List<Pedido> findByClienteId(String clienteId);
    List<Pedido> findAll();
}