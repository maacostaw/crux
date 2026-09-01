package com.micro.productos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.productos.application.dtos.ProductoRequest;
import com.micro.productos.infrastructure.rest.ProductoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deberiaCrearYObtenerUnProducto() throws Exception {
        ProductoRequest request = new ProductoRequest("Producto", BigDecimal.valueOf(160000.00), 10);

        // Creo el producto y verifico un 201 de respuesta
        String responseJson = mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Producto"))
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(responseJson).get("id").asLong();

        mockMvc.perform(get("/api/productos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precio").value(160000.00))
                .andExpect(jsonPath("$.stock").value(10));
    }

    @Test
    void deberiaRetornar400CuandoElNombreEstaVacio() throws Exception {
        ProductoRequest request = new ProductoRequest("", BigDecimal.valueOf(160000.00), 10);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Errors.nombre").exists());
    }

    @Test
    void deberiaRetornar404CuandoElProductoNoExiste() throws Exception {
        mockMvc.perform(get("/api/productos/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.Message").exists());
    }
}
