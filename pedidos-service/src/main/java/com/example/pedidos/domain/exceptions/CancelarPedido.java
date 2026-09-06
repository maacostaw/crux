package com.example.pedidos.domain.exceptions;

import com.example.pedidos.domain.enums.PedidoStatus;

public class CancelarPedido extends RuntimeException {
    public CancelarPedido(Long id, PedidoStatus status) {
        super(construirMensaje(id, status));
    }

    private static String construirMensaje(Long id, PedidoStatus status) {
        if (status.equals(PedidoStatus.CANCELLED)) {
            return String.format("El pedido: %d, ya se encuentra cancelado", id);
        } else if (status.equals(PedidoStatus.PENDING_CANCELLATION)) {
            return String.format("El pedido: %d, está en proceso de cancelarse", id);
        } else {
            return "otro problema";
        }
    }
}
