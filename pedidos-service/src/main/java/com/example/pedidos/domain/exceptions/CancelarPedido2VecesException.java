package com.example.pedidos.domain.exceptions;

public class CancelarPedido2VecesException extends RuntimeException {
    public CancelarPedido2VecesException(Long id) {
        super(String.format("El pedido: %d, ya se encuentra cancelado", id));
    }
}
