package com.micro.productos.domain.exceptions;

public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String message){
        super(message);
    }
}
