package com.example.pedidos.application.dtos;

import java.math.BigDecimal;

// Clase que representa un producto del servicio de productos
public class ProductoExternal {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;

    public ProductoExternal() {
    }

    public ProductoExternal(Long id, Integer stock, BigDecimal precio, String nombre) {
        this.id = id;
        this.stock = stock;
        this.precio = precio;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
