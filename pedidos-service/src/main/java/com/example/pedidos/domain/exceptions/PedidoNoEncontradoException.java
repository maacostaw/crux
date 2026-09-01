package com.example.pedidos.domain.exceptions;

public class PedidoNoEncontradoException extends RuntimeException {
    public PedidoNoEncontradoException(Long pedidoId) {
        super("No se encuentra el pedido con el id: " + pedidoId);
    }
}
