package com.example.pedidos.domain.objects;

import com.example.pedidos.domain.enums.PedidoStatus;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pedido {
    private Long id;
    private Long idProducto;
    private String nombre;
    private BigDecimal precioUnitario;
    private Integer cantidad;
    private PedidoStatus status;
    private LocalDateTime createdAt;

    public Pedido() {
    }

    public Pedido(Long id, Long idProducto, String nombre, BigDecimal precioUnitario, Integer cantidad, PedidoStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PedidoStatus getStatus() {
        return status;
    }

    public void setStatus(PedidoStatus status) {
        this.status = status;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }
}
