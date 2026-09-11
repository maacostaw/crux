package com.micro.productos.domain.ports;

public interface ProductoEventPort {
    void publicarStockDevuelto(Long pedidoId, Long productoId, Boolean success);
}
