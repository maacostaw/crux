package com.micro.productos.application.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateStockRequest {
    @NotNull
    @Positive(message = "El valor de stock a actualizar debe ser positivo")
    private Integer quantity;

    public UpdateStockRequest() {
    }

    public UpdateStockRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

