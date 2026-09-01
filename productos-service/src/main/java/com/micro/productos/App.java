package com.micro.productos;

import com.micro.productos.infrastructure.persistence.entities.ProductoEntity;
import com.micro.productos.infrastructure.persistence.repositories.ProductoJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    // Sembramos un par de productos al arrancar, para no tener que crearlos a mano.
    @Bean
    public CommandLineRunner seedData(ProductoJpaRepository repository) {
        ProductoEntity p1 = new ProductoEntity();
        p1.setId(null);
        p1.setNombre("Teclado mecánico");
        p1.setPrecio(BigDecimal.valueOf(250000.00));
        p1.setStock(10);

        ProductoEntity p2 = new ProductoEntity();
        p2.setId(null);
        p2.setNombre("Mouse inalámbrico");
        p2.setPrecio(BigDecimal.valueOf(80000.00));
        p2.setStock(5);

        return args -> {
            repository.save(p1);
            repository.save(p2);
        };
    }
}
