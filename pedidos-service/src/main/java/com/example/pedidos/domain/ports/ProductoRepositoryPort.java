package com.example.pedidos.domain.ports;

import com.example.pedidos.domain.objects.Producto;

public interface ProductoRepositoryPort {

    Producto reduceStock(Long productId, Integer quantity);
    void augmentStock(Long productId, Integer quantity);
}
