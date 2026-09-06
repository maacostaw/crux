package com.micro.productos.domain.objects;

import java.time.LocalDateTime;

public class EventoProcesado {
    private String eventId;
    private LocalDateTime procesadoEn;

    public EventoProcesado(){}

    public EventoProcesado(String eventId) {
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
