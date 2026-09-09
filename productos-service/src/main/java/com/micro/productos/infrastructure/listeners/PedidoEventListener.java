package com.micro.productos.infrastructure.listeners;

import com.micro.productos.application.dtos.PedidoEvent;
import com.micro.productos.domain.useCases.ActualizarInventarioUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoEventListener {

    private final ActualizarInventarioUseCase actualizarInventarioUseCase;

    public PedidoEventListener(ActualizarInventarioUseCase actualizarInventarioUseCase) {
        this.actualizarInventarioUseCase = actualizarInventarioUseCase;
    }

    // Esta función confía en los mensajes que le llega
    // Garantizamos la lógica de negocio en los productores
    // Y garantizamos seguridad en el tópico para que solo ellos puedan publicar
    @KafkaListener(topics = "pedido-cancelado", containerFactory = "pedidoCanceladoListenerFactory")
    public void escucharPedidoCancelado(PedidoEvent evento) {
        this.actualizarInventarioUseCase.aumentarInventario(evento);
    }
}
