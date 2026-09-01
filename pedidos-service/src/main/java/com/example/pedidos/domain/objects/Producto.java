package com.example.pedidos.domain.objects;

import java.math.BigDecimal;

public class Producto {
    private Long id;
    private String nombre;
    private BigDecimal precio;

    public Producto() {
    }

    public Producto(Long id, BigDecimal precio, String nombre) {
        this.id = id;
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
}
