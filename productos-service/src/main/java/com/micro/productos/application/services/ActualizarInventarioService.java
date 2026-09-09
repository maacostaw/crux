package com.micro.productos.application.services;

import com.micro.productos.application.dtos.PedidoEvent;
import com.micro.productos.application.dtos.ProductoResponse;
import com.micro.productos.application.dtos.UpdateStockRequest;
import com.micro.productos.domain.exceptions.RecursoNoEncontradoException;
import com.micro.productos.domain.exceptions.StockInsuficienteException;
import com.micro.productos.domain.objects.EventoProcesado;
import com.micro.productos.domain.objects.Producto;
import com.micro.productos.domain.ports.EventoProcesadoRepositoryPort;
import com.micro.productos.domain.ports.ProductoRepositoryPort;
import com.micro.productos.domain.useCases.ActualizarInventarioUseCase;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActualizarInventarioService implements ActualizarInventarioUseCase {

    private static final Logger log = LoggerFactory.getLogger(ActualizarInventarioService.class);

    private final ProductoRepositoryPort productoRepository;
    private final EventoProcesadoRepositoryPort eventoProcesadoRepository;

    private final ModelMapper modelMapper;

    public ActualizarInventarioService(
            ProductoRepositoryPort productoRepository,
            EventoProcesadoRepositoryPort eventoProcesadoRepository,
            ModelMapper modelMapper
    ){
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
        this.eventoProcesadoRepository = eventoProcesadoRepository;
    }

    @Override
    public ProductoResponse reducirInventario(Long id, UpdateStockRequest request) {
        Producto producto = this.productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra el producto con id: " + id));

        if(producto.getStock() < request.getQuantity()){
            throw new StockInsuficienteException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        producto.setStock(producto.getStock() - request.getQuantity());
        Producto productoGuardado = this.productoRepository.save(producto);
        return this.modelMapper.map(productoGuardado, ProductoResponse.class);
    }

    @Override
    public void aumentarInventario(PedidoEvent evento){
        log.info("Evento recibido: pedido cancelado, eventId={}, pedidoId={}", evento.getEventId(), evento.getPedidoId());

        // Garantizamos idempotencia
        if(this.eventoProcesadoRepository.existsById(evento.getEventId())){
            log.info("Evento {} ya fue procesado antes, ignorando", evento.getEventId());
            return;
        }

        // Buscamos el producto para devolverle su stock
        Optional<Producto> productoOpt = this.productoRepository.findById(evento.getProductoId());
        if(productoOpt.isEmpty()){
            log.info("No se encontró el producto con id={}, no se pudo devolver el stock", evento.getProductoId());
            return;
        }

        // Guardamos el producto actualizado
        Producto producto = productoOpt.get();
        producto.setStock(producto.getStock() + evento.getCantidad());
        this.productoRepository.save(producto);

        // Guardamos que procesamos el evento
        eventoProcesadoRepository.save(new EventoProcesado(evento.getEventId()));

        log.info("Stock devuelto para productId={}, cantidad={}", evento.getProductoId(), evento.getCantidad());
    }
}
