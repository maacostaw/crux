package com.micro.productos.infrastructure.rest;

import com.micro.productos.domain.exceptions.RecursoNoEncontradoException;
import com.micro.productos.domain.exceptions.StockInsuficienteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> RecursoNoEncontradoHandler(RecursoNoEncontradoException e){
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.NOT_FOUND;
        response.put("Timestamp", LocalDateTime.now());
        response.put("Status", status);
        response.put("Message", e.getMessage());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<Map<String, Object>> StockInsuficienteHandler(StockInsuficienteException e){
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.CONFLICT;
        response.put("Timestamp", LocalDateTime.now());
        response.put("Status", status);
        response.put("Message", e.getMessage());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for(FieldError error : e.getBindingResult().getFieldErrors()){
            errors.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        response.put("Timestamp", LocalDateTime.now());
        response.put("Status", status);
        response.put("Errors", errors);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> exceptionHandler(Exception e){
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        response.put("Timestamp", LocalDateTime.now());
        response.put("Status", status);
        response.put("Message", e.getMessage());
        return ResponseEntity.status(status).body(response);
    }
}
