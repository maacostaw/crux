package com.example.pedidos.infrastructure.persistence.repository;

import com.example.pedidos.infrastructure.persistence.model.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
}
