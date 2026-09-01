package com.micro.productos.application.services;

import com.micro.productos.application.dtos.ProductoRequest;
import com.micro.productos.application.dtos.ProductoResponse;
import com.micro.productos.domain.exceptions.RecursoNoEncontradoException;
import com.micro.productos.domain.objects.Producto;
import com.micro.productos.domain.ports.ProductoRepositoryPort;
import com.micro.productos.domain.useCases.ProductoUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements ProductoUseCase {
    private final ProductoRepositoryPort productoRepositoryPort;

    private final ModelMapper modelMapper;

    public ProductoService(ProductoRepositoryPort productoRepositoryPort, ModelMapper modelMapper){
        this.productoRepositoryPort = productoRepositoryPort;
        this.modelMapper = modelMapper;
    }

    public List<ProductoResponse> getAll() {
        return this.productoRepositoryPort.findAll().stream()
                .map(p -> this.modelMapper.map(p, ProductoResponse.class))
                .toList();
    }

    public ProductoResponse getById(Long id){
        Producto producto = this.productoRepositoryPort.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra el producto con id: " + id));
        return this.modelMapper.map(producto, ProductoResponse.class);

    }

    public ProductoResponse crear(ProductoRequest productoRequest){
        Producto producto = this.modelMapper.map(productoRequest, Producto.class);
        Producto productoGuardado = this.productoRepositoryPort.save(producto);
        return this.modelMapper.map(productoGuardado, ProductoResponse.class);
    }
}
