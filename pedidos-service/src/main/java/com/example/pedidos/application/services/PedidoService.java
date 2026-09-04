package com.example.pedidos.application.services;

import com.example.pedidos.application.dtos.PedidoResponse;
import com.example.pedidos.domain.enums.PedidoStatus;
import com.example.pedidos.domain.exceptions.CancelarPedido2VecesException;
import com.example.pedidos.domain.exceptions.PedidoNoEncontradoException;
import com.example.pedidos.domain.objects.Pedido;
import com.example.pedidos.domain.ports.PedidoEventPort;
import com.example.pedidos.domain.ports.PedidoRepositoryPort;
import com.example.pedidos.domain.ports.ProductoRepositoryPort;
import com.example.pedidos.application.dtos.PedidoRequest;
import com.example.pedidos.domain.objects.Producto;
import com.example.pedidos.domain.useCases.PedidoUseCase;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService implements PedidoUseCase {

    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepositoryPort pedidoRepositoryPort;
    private final ProductoRepositoryPort productoRepositoryPort;
    private final ModelMapper modelMapper;

    private final PedidoEventPort pedidoEventPort;

    public PedidoService(PedidoRepositoryPort pedidoRepositoryPort,
                         PedidoEventPort pedidoEventPort,
                         ProductoRepositoryPort productoRepositoryPort,
                         ModelMapper modelMapper
    ) {
        this.productoRepositoryPort = productoRepositoryPort;
        this.pedidoEventPort = pedidoEventPort;
        this.pedidoRepositoryPort = pedidoRepositoryPort;
        this.modelMapper = modelMapper;
    }

    public List<PedidoResponse> getAll() {
        return pedidoRepositoryPort.findAll().stream()
                .map(pedido -> modelMapper.map(pedido, PedidoResponse.class))
                .toList();
    }

    public PedidoResponse getById(Long id) {
        Pedido pedido = this.pedidoRepositoryPort.findById(id)
                .orElseThrow(() -> new PedidoNoEncontradoException(id));
        return this.modelMapper.map(pedido, PedidoResponse.class);
    }

    // Caso de uso principal: crear un pedido.
    public PedidoResponse crearPedido(PedidoRequest request) {
        log.info("Creando pedido para productId={} quantity={}", request.getProductoId(), request.getCantidad());

        // 1) Intentar descontar el stock en products-service y guardar el producto resultante
        Producto producto = this.productoRepositoryPort.reduceStock(request.getProductoId(), request.getCantidad());

        Pedido pedido = new Pedido();
        pedido.setId(null);
        pedido.setProductoId(producto.getId());
        pedido.setNombre(producto.getNombre());
        pedido.setPrecioUnitario(producto.getPrecio());
        pedido.setCantidad(request.getCantidad());
        pedido.setStatus(PedidoStatus.CREATED);
        pedido.setCreatedAt(LocalDateTime.now());

        // 2) guardar el pedido localmente con estado CREATED
        Pedido pedidoGuardado = this.pedidoRepositoryPort.save(pedido);

        log.info("Pedido creado con id={}", pedidoGuardado.getId());
        return this.modelMapper.map(pedidoGuardado, PedidoResponse.class);
    }

    public PedidoResponse cancelarPedido(Long id){
        Pedido pedido = this.pedidoRepositoryPort.findById(id)
                .orElseThrow(() -> new PedidoNoEncontradoException(id));

        if(pedido.getStatus().equals(PedidoStatus.CANCELLED)){
            throw new CancelarPedido2VecesException(pedido.getId());
        }

        pedido.setStatus(PedidoStatus.PENDING_CANCELLATION);
        Pedido savedPedido = this.pedidoRepositoryPort.save(pedido);

        this.pedidoEventPort.publicarPedidoCancelado(pedido.getId(), pedido.getProductoId(), pedido.getCantidad());

        return this.modelMapper.map(savedPedido, PedidoResponse.class);
    }
}
