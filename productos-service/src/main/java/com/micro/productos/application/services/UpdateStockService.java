package com.micro.productos.application.services;

import com.micro.productos.application.dtos.ProductoResponse;
import com.micro.productos.application.dtos.UpdateStockRequest;
import com.micro.productos.domain.exceptions.RecursoNoEncontradoException;
import com.micro.productos.domain.exceptions.StockInsuficienteException;
import com.micro.productos.domain.objects.Producto;
import com.micro.productos.domain.ports.ProductoRepositoryPort;
import com.micro.productos.domain.useCases.UpdateStockUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UpdateStockService implements UpdateStockUseCase {

    private final ProductoRepositoryPort productoRepositoryPort;

    private final ModelMapper modelMapper;

    public UpdateStockService(ProductoRepositoryPort productoRepositoryPort, ModelMapper modelMapper){
        this.productoRepositoryPort = productoRepositoryPort;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductoResponse reduceStock(Long id, UpdateStockRequest request) {
        Producto producto = this.productoRepositoryPort.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra el producto con id: " + id));

        if(producto.getStock() < request.getQuantity()){
            throw new StockInsuficienteException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        producto.setStock(producto.getStock() - request.getQuantity());
        Producto productoGuardado = this.productoRepositoryPort.save(producto);
        return this.modelMapper.map(productoGuardado, ProductoResponse.class);
    }

    @Override
    public void augmentStock(Long id, UpdateStockRequest request){
        Producto producto = this.productoRepositoryPort.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra el producto con id: " + id));

        producto.setStock(producto.getStock() + request.getQuantity());
        this.productoRepositoryPort.save(producto);
    }
}
