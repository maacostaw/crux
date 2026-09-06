package com.micro.productos.infrastructure.persistence.adapters;

import com.micro.productos.domain.objects.EventoProcesado;
import com.micro.productos.domain.ports.EventoProcesadoRepositoryPort;
import com.micro.productos.infrastructure.persistence.entities.EventoProcesadoEntity;
import com.micro.productos.infrastructure.persistence.repositories.EventoProcesadoJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EventoProcesadoRepository implements EventoProcesadoRepositoryPort {
    private final EventoProcesadoJpaRepository eventoProcesadoJpaRepository;

    private final ModelMapper modelMapper;

    public EventoProcesadoRepository(
            EventoProcesadoJpaRepository eventoProcesadoJpaRepository,
            ModelMapper modelMapper
    ) {
        this.eventoProcesadoJpaRepository = eventoProcesadoJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean existsById(String eventId) {
        return this.eventoProcesadoJpaRepository.existsById(eventId);
    }

    @Override
    public EventoProcesado save(EventoProcesado evento) {
        EventoProcesadoEntity eventoProcesadoEntity = this.modelMapper.map(evento, EventoProcesadoEntity.class);
        EventoProcesadoEntity eventoProcesadoCreado = this.eventoProcesadoJpaRepository.save(eventoProcesadoEntity);
        return this.modelMapper.map(eventoProcesadoCreado, EventoProcesado.class);
    }
}
