package com.micro.productos.domain.ports;

import com.micro.productos.domain.objects.EventoProcesado;

import java.util.Optional;

public interface EventoProcesadoRepositoryPort {
    boolean existsById(String eventId);

    EventoProcesado save(EventoProcesado evento);
}
