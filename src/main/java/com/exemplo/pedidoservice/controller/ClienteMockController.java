package com.exemplo.pedidoservice.controller;

import com.exemplo.pedidoservice.dto.ClienteDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteMockController {

    @GetMapping
    public List<ClienteDTO> listarClientes() {
        ClienteDTO a = new ClienteDTO("10", "Ana", "ana@email.com");
        ClienteDTO b = new ClienteDTO("11", "Bruno", "bruno@email.com");
        return List.of(a, b);
    }

    @GetMapping("/{clienteId}")
    public ClienteDTO detalhesCliente(@PathVariable String clienteId) {
        return new ClienteDTO(clienteId, "Cliente " + clienteId, clienteId + "@fake.com");
    }
}
