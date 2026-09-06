package com.micro.productos.infrastructure.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class EventoProcesadoEntity {
    @Id
    private String eventId;
    private LocalDateTime procesadoEn;

    public EventoProcesadoEntity(){}

    public EventoProcesadoEntity(String eventId) {
        this.eventId = eventId;
        this.procesadoEn = LocalDateTime.now();
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getProcesadoEn() {
        return procesadoEn;
    }

    public void setProcesadoEn(LocalDateTime procesadoEn) {
        this.procesadoEn = procesadoEn;
    }
}
