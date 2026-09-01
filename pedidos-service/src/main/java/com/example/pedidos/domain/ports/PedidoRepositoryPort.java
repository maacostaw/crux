package com.example.pedidos.domain.ports;

import com.example.pedidos.domain.objects.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoRepositoryPort {
    List<Pedido> findAll();

    Optional<Pedido> findById(Long id);

    Pedido save(Pedido pedido);
}
