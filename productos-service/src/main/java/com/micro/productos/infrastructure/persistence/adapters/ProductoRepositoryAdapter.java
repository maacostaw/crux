package com.micro.productos.infrastructure.persistence.adapters;

import com.micro.productos.domain.objects.Producto;
import com.micro.productos.domain.ports.ProductoRepositoryPort;
import com.micro.productos.infrastructure.persistence.entities.ProductoEntity;
import com.micro.productos.infrastructure.persistence.repositories.ProductoJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepositoryAdapter implements ProductoRepositoryPort {
    private final ProductoJpaRepository productoJpaRepository;

    private final ModelMapper modelMapper;

    public ProductoRepositoryAdapter(ProductoJpaRepository productoJpaRepository, ModelMapper modelMapper) {
        this.productoJpaRepository = productoJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Producto> findAll(){
        return this.productoJpaRepository.findAll().stream()
                .map(p -> this.modelMapper.map(p, Producto.class))
                .toList();
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return this.productoJpaRepository.findById(id)
                .map(p -> this.modelMapper.map(p, Producto.class));
    }

    @Override
    public Producto save(Producto producto){
        ProductoEntity productoEntity = modelMapper.map(producto, ProductoEntity.class);
        ProductoEntity productoCreado = this.productoJpaRepository.save(productoEntity);
        return modelMapper.map(productoCreado, Producto.class);
    }
}
