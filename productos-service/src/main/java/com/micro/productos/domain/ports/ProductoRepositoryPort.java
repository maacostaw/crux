package com.micro.productos.domain.ports;

import com.micro.productos.domain.objects.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepositoryPort {
    List<Producto> findAll();

    Optional<Producto> findById(Long id);

    Producto save(Producto producto);
}
