package com.example.pedidos.infrastructure.publishers;

import com.example.pedidos.application.dtos.PedidoCanceladoEvent;
import com.example.pedidos.domain.ports.PedidoEventPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PedidoEventPublisher implements PedidoEventPort {
    private static final String TOPIC = "pedido-cancelado";

    private final KafkaTemplate<String, PedidoCanceladoEvent> kafkaTemplate;

    public PedidoEventPublisher(KafkaTemplate<String, PedidoCanceladoEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publicarPedidoCancelado(Long pedidoId, Long productoId, Integer cantidad) {
        PedidoCanceladoEvent evento = new PedidoCanceladoEvent(pedidoId,productoId, cantidad);
        kafkaTemplate.send(TOPIC, pedidoId.toString(), evento);
    }
}
