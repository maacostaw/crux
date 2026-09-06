package com.example.pedidos.application.dtos;

import java.util.UUID;

public class PedidoCanceladoEvent {
    private String eventId;
    private Long pedidoId;
    private Long productoId;
    private Integer cantidad;

    public PedidoCanceladoEvent() {

    }

    public PedidoCanceladoEvent(Long pedidoId, Long productoId, Integer cantidad) {
        this.eventId = UUID.randomUUID().toString();
        this.pedidoId = pedidoId;
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
