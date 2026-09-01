package com.micro.productos.domain.useCases;

import com.micro.productos.application.dtos.ProductoRequest;
import com.micro.productos.application.dtos.ProductoResponse;

import java.util.List;

public interface ProductoUseCase {
    List<ProductoResponse> getAll();
    ProductoResponse getById(Long id);
    ProductoResponse crear(ProductoRequest productoRequest);
}
