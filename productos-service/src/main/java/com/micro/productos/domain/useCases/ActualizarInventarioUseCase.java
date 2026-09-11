package com.micro.productos.domain.useCases;

import com.micro.productos.application.dtos.Events.PedidoCanceladoEvent;
import com.micro.productos.application.dtos.Responses.ProductoResponse;
import com.micro.productos.application.dtos.Requests.UpdateStockRequest;

public interface ActualizarInventarioUseCase {
    ProductoResponse reducirInventario(Long id, UpdateStockRequest request);
    void aumentarInventario(PedidoCanceladoEvent event);
}
