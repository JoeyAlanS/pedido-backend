package com.exemplo.pedidoservice.dto;

public class PagamentoRequestDTO {
    private String pedidoId;
    private Double valor;

    public String getPedidoId() { return pedidoId; }
    public void setPedidoId(String pedidoId) { this.pedidoId = pedidoId; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
}