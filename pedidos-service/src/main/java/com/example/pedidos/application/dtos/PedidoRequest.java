package com.example.pedidos.application.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PedidoRequest {

    @NotNull
    @Positive(message = "El id del producto debe ser positivo")
    private Long productoId;

    @NotNull
    @Positive(message = "La cantidad de items del pedido debe ser positiva")
    private Integer cantidad;

    public PedidoRequest(Long productoId, Integer cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public Long getProductoId() {
        return productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
