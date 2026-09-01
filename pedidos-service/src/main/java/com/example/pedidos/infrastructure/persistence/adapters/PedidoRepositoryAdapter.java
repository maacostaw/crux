package com.example.pedidos.infrastructure.persistence.adapters;

import com.example.pedidos.domain.objects.Pedido;
import com.example.pedidos.domain.ports.PedidoRepositoryPort;
import com.example.pedidos.infrastructure.persistence.model.PedidoEntity;
import com.example.pedidos.infrastructure.persistence.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PedidoRepositoryAdapter implements PedidoRepositoryPort {

    private final PedidoRepository pedidoRepository;
    private final ModelMapper modelMapper;

    public PedidoRepositoryAdapter(PedidoRepository pedidoRepository, ModelMapper modelMapper) {
        this.pedidoRepository = pedidoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Pedido> findAll() {
        return this.pedidoRepository.findAll().stream()
                .map(pedidoE -> modelMapper.map(pedidoE, Pedido.class))
                .toList();
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return this.pedidoRepository.findById(id)
                .map(pedidoE -> modelMapper.map(pedidoE, Pedido.class));
    }

    @Override
    public Pedido save(Pedido pedido) {
        PedidoEntity pedidoEntity = modelMapper.map(pedido, PedidoEntity.class);
        PedidoEntity pedidoCreado = this.pedidoRepository.save(pedidoEntity);
        return modelMapper.map(pedidoCreado, Pedido.class);
    }
}
