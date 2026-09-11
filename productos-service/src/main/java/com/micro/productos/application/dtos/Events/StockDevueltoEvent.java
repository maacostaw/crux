package com.micro.productos.application.dtos.Events;

import java.util.UUID;

public class StockDevueltoEvent {
    private String eventId;
    private Long pedidoId;
    private Long productoId;
    private Boolean success;

    public StockDevueltoEvent() {
    }

    public StockDevueltoEvent(Long pedidoId, Long productoId, Boolean success) {
        this.eventId = UUID.randomUUID().toString();
        this.pedidoId = pedidoId;
        this.productoId = productoId;
        this.success = success;
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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
