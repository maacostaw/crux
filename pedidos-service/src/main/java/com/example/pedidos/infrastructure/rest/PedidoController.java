package com.example.pedidos.infrastructure.rest;

import com.example.pedidos.application.dtos.PedidoResponse;
import com.example.pedidos.application.services.PedidoService;
import com.example.pedidos.application.dtos.PedidoRequest;
import com.example.pedidos.domain.useCases.PedidoUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    private final PedidoUseCase pedidoUseCase;

    public PedidoController(PedidoUseCase pedidoUseCase) {
        this.pedidoUseCase = pedidoUseCase;
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> getAll() {
        List<PedidoResponse> pedidos = this.pedidoUseCase.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> getById(@PathVariable Long id) {
        PedidoResponse pedido = this.pedidoUseCase.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(pedido);
    }

    // Caso de uso #1 crear un pedido
    @PostMapping
    public ResponseEntity<PedidoResponse> crearPedido(@Valid @RequestBody PedidoRequest request) {
        PedidoResponse pedido = this.pedidoUseCase.crearPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    // Caso de uso #2 cancelar un pedido
    @PostMapping("/{id}/cancelar-pedido")
    public ResponseEntity<PedidoResponse> cancelarPedido(@PathVariable Long id) {
        PedidoResponse pedido = this.pedidoUseCase.cancelarPedido(id);
        return ResponseEntity.status(HttpStatus.OK).body(pedido);
    }
}
