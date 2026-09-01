package com.example.pedidos.domain.exceptions;

public class ProductoServiceNoDisponibleException extends RuntimeException {
    public ProductoServiceNoDisponibleException(Throwable cause) {
        super("No se pudo contactar products-service", cause);
    }
}
