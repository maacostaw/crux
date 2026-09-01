package com.example.pedidos.domain.exceptions;

public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(Long productoId) {
        super("Stock insuficiente para el producto: " + productoId);
    }
}
