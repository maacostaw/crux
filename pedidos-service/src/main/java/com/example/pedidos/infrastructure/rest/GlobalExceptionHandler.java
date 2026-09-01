package com.example.pedidos.infrastructure.rest;

import com.example.pedidos.domain.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(PedidoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> PedidoNoEncontradoHandler(PedidoNoEncontradoException e){
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.NOT_FOUND;
        response.put("Timestamp", LocalDateTime.now());
        response.put("Status", status);
        response.put("Message", e.getMessage());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(ProductoNoEncotradoException.class)
    public ResponseEntity<Map<String, Object>> ProductoNoEncotradoHandler(ProductoNoEncotradoException e){
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

    @ExceptionHandler(ProductoServiceNoDisponibleException.class)
    public ResponseEntity<Map<String, Object>> ProductoServiceNoDisponibleHandler(ProductoServiceNoDisponibleException e) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        response.put("Timestamp", LocalDateTime.now());
        response.put("Status", status);
        response.put("Message", e.getMessage());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(CancelarPedido2VecesException.class)
    public ResponseEntity<Map<String, Object>> CancelarPedido2VecesHandler(CancelarPedido2VecesException e) {
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
