package com.micro.productos.infrastructure.rest;

import com.micro.productos.application.dtos.ProductoRequest;
import com.micro.productos.application.dtos.ProductoResponse;
import com.micro.productos.application.dtos.UpdateStockRequest;
import com.micro.productos.domain.useCases.ProductoUseCase;
import com.micro.productos.domain.useCases.UpdateStockUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/productos")
public class ProductoController {

    private final ProductoUseCase productoUseCase;

    private final UpdateStockUseCase updateStockUseCase;

    public ProductoController(ProductoUseCase productoUseCase, UpdateStockUseCase updateStockUseCase) {
        this.productoUseCase = productoUseCase;
        this.updateStockUseCase = updateStockUseCase;
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> getAll() {
        List<ProductoResponse> productos = productoUseCase.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> getById(@PathVariable Long id) {
        ProductoResponse producto = this.productoUseCase.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(producto);
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> create(@Valid @RequestBody ProductoRequest productoRequest) {
        ProductoResponse producto = this.productoUseCase.crear(productoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    // Este es el endpoint clave para la comunicación entre microservicios:
    // orders-service llama aquí para descontar stock cuando se crea un pedido.
    @PostMapping("/{id}/reduce-stock")
    public ResponseEntity<ProductoResponse> reduceStock(@PathVariable Long id, @RequestBody UpdateStockRequest request) {
        ProductoResponse producto = this.updateStockUseCase.reduceStock(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(producto);
    }

    @PostMapping("/{id}/augment-stock")
    public ResponseEntity<Void> augmentStock(@PathVariable Long id, @RequestBody UpdateStockRequest request) {
        this.updateStockUseCase.augmentStock(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
