package com.example.pedidos.domain.exceptions;

public class ProductoNoEncotradoException extends RuntimeException {
    public ProductoNoEncotradoException(Long productoId) {
        super("Producto no encontrado: " + productoId);
    }
}
