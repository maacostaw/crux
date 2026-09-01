package com.example.pedidos.infrastructure.clients;

import com.example.pedidos.application.dtos.ProductoExternal;
import com.example.pedidos.domain.exceptions.StockInsuficienteException;
import com.example.pedidos.domain.exceptions.ProductoServiceNoDisponibleException;
import com.example.pedidos.domain.objects.Producto;
import com.example.pedidos.domain.exceptions.ProductoNoEncotradoException;
import com.example.pedidos.domain.ports.ProductoRepositoryPort;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

// Toda la comunicacion HTTP hacia products-service vive aca, aislada del resto del codigo.
// Si mañana cambia products-service (otra URL, otro protocolo), solo se toca esta clase.
@Component
public class ProductClient implements ProductoRepositoryPort {

    private final RestTemplate restTemplate;
    private final String productsServiceUrl;
    private final ModelMapper modelMapper;

    public ProductClient(RestTemplate restTemplate,
                         @Value("${products.service.url}") String productsServiceUrl,
                         ModelMapper modelMapper
    ) {
        this.restTemplate = restTemplate;
        this.productsServiceUrl = productsServiceUrl;
        this.modelMapper = modelMapper;
    }

    public Producto reduceStock(Long productId, Integer quantity) {
        try {
            ProductoExternal productoExternal = restTemplate.postForEntity(
                    productsServiceUrl + "/api/productos/" + productId + "/reduce-stock",
                    Map.of("quantity", quantity),
                    ProductoExternal.class
            ).getBody();
            return modelMapper.map(productoExternal, Producto.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ProductoNoEncotradoException(productId);
        } catch (HttpClientErrorException.Conflict e) {
            throw new StockInsuficienteException(productId);
        } catch (ResourceAccessException e) {
            throw new ProductoServiceNoDisponibleException(e);
        }
    }

    @Override
    public void augmentStock(Long productId, Integer quantity) {
        try {
            restTemplate.postForEntity(
                    productsServiceUrl + "/api/productos/" + productId + "/augment-stock",
                    Map.of("quantity", quantity),
                    Void.class
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new ProductoNoEncotradoException(productId);
        } catch (ResourceAccessException e) {
            throw new ProductoServiceNoDisponibleException(e);
        }
    }
}
