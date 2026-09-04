package com.example.pedidos.domain.ports;

public interface PedidoEventPort {
    void publicarPedidoCancelado(Long pedidoId, Long productoId, Integer quantity);
}
