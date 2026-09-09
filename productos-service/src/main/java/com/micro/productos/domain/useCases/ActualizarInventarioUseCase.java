package com.micro.productos.domain.useCases;

import com.micro.productos.application.dtos.PedidoEvent;
import com.micro.productos.application.dtos.ProductoResponse;
import com.micro.productos.application.dtos.UpdateStockRequest;

public interface ActualizarInventarioUseCase {
    ProductoResponse reducirInventario(Long id, UpdateStockRequest request);
    void aumentarInventario(PedidoEvent event);
}
