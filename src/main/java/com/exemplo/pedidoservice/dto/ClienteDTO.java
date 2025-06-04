package com.exemplo.pedidoservice.dto;

public class ClienteDTO {
    private String id;
    private String nome;
    private String email;

    public ClienteDTO(String id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }
}

