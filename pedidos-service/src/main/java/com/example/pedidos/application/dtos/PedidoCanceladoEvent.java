package com.example.pedidos.application.dtos;

import java.util.UUID;

public class PedidoCanceladoEvent {
    private String eventId;
    private Long orderId;
    private Long productId;
    private Integer quantity;

    public PedidoCanceladoEvent() {

    }

    public PedidoCanceladoEvent(Long orderId, Long productId, Integer quantity) {
        this.eventId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
