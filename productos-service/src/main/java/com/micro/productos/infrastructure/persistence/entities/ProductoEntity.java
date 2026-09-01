package com.micro.productos.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;

    public ProductoEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
