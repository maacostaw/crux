package com.micro.productos.domain.useCases;

import com.micro.productos.application.dtos.ProductoResponse;
import com.micro.productos.application.dtos.UpdateStockRequest;
import com.micro.productos.domain.objects.Producto;

public interface UpdateStockUseCase {
    ProductoResponse reduceStock(Long id, UpdateStockRequest request);
    void augmentStock(Long id, UpdateStockRequest request);
}
