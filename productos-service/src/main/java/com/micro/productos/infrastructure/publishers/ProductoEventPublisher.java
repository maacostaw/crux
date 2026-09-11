package com.micro.productos.infrastructure.publishers;

import com.micro.productos.application.dtos.Events.PedidoCanceladoEvent;
import com.micro.productos.application.dtos.Events.StockDevueltoEvent;
import com.micro.productos.domain.ports.ProductoEventPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductoEventPublisher implements ProductoEventPort {
    private static final String TOPIC = "productos.stock-devuelto";

    private final KafkaTemplate<String, StockDevueltoEvent> kafkaTemplate;

    public ProductoEventPublisher(KafkaTemplate<String, StockDevueltoEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void publicarStockDevuelto(Long pedidoId, Long productoId, Boolean success) {
        StockDevueltoEvent evento = new StockDevueltoEvent(pedidoId,productoId,success);
        kafkaTemplate.send(TOPIC, pedidoId.toString(), evento);
    }
}
