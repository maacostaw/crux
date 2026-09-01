package com.micro.productos.infrastructure.persistence.repositories;

import com.micro.productos.infrastructure.persistence.entities.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoJpaRepository extends JpaRepository<ProductoEntity, Long> {
}
