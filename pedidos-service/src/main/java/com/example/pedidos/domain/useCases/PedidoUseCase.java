package com.example.pedidos.domain.useCases;

import com.example.pedidos.application.dtos.PedidoRequest;
import com.example.pedidos.application.dtos.PedidoResponse;

import java.util.List;

public interface PedidoUseCase {
    List<PedidoResponse> getAll();
    PedidoResponse getById(Long id);
    PedidoResponse crearPedido(PedidoRequest request);
    PedidoResponse cancelarPedido(Long id);
}
