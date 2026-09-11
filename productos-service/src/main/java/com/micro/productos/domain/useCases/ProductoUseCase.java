package com.micro.productos.domain.useCases;

import com.micro.productos.application.dtos.Requests.ProductoRequest;
import com.micro.productos.application.dtos.Responses.ProductoResponse;

import java.util.List;

public interface ProductoUseCase {
    List<ProductoResponse> getAll();
    ProductoResponse getById(Long id);
    ProductoResponse crear(ProductoRequest productoRequest);
}
