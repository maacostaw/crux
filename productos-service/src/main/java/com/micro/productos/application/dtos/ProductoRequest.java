package com.micro.productos.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ProductoRequest {

    @NotNull
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull
    @Positive(message="El precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotNull
    @Positive(message = "El stock debe ser mayor a 0")
    private Integer stock;

    public ProductoRequest() {
    }

    public ProductoRequest(String nombre, BigDecimal precio, Integer stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
