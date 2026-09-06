package com.micro.productos.infrastructure.persistence.repositories;

import com.micro.productos.infrastructure.persistence.entities.EventoProcesadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoProcesadoJpaRepository extends JpaRepository<EventoProcesadoEntity, String> {
}
