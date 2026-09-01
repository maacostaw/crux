package com.micro.productos.application.dtos;

import java.math.BigDecimal;

public class ProductoResponse {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;

    public ProductoResponse(){

    }

    public Long getId() {
        return id;
    }

    public Integer getStock() {
        return stock;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(Long id) {
        this.id = id;
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
