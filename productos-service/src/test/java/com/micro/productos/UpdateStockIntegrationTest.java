package com.micro.productos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.productos.application.dtos.ProductoRequest;
import com.micro.productos.application.dtos.UpdateStockRequest;
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
public class UpdateStockIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deberiaRestarElStock() throws Exception {
        ProductoRequest request1 = new ProductoRequest("Producto", BigDecimal.valueOf(100000.00), 10);

        // Creamos el producto
        String response1Json = mockMvc.perform(post("/api/productos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsString();

        Long productoId = objectMapper.readTree(response1Json).get("id").asLong();
        Integer productoStock = objectMapper.readTree(response1Json).get("stock").asInt();

        UpdateStockRequest request2 = new UpdateStockRequest(productoStock-1);

        //Realizamos la operación de restar stock
        mockMvc.perform(post("/api/productos/" + productoId + "/reduce-stock")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productoId))
                .andExpect(jsonPath("$.stock").value(productoStock- request2.getQuantity()));

        mockMvc.perform(get("/api/productos/" + productoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productoId))
                .andExpect(jsonPath("$.stock").value(productoStock- request2.getQuantity()));
    }

    @Test
    void deberiaRetornar404CuandoElProductoNoExiste() throws Exception {
        UpdateStockRequest request = new UpdateStockRequest(3);

        //Realizamos la operación de restar stock
        mockMvc.perform(post("/api/productos/999999/reduce-stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.Message").exists());
    }

    @Test
    void deberiaRetornar409CuandoElStockEsInsuficiente() throws Exception {
        ProductoRequest request1 = new ProductoRequest("Producto", BigDecimal.valueOf(100000.00), 10);

        // Creamos el producto
        String response1Json = mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsString();

        Long productoId = objectMapper.readTree(response1Json).get("id").asLong();
        Integer productoStock = objectMapper.readTree(response1Json).get("stock").asInt();

        UpdateStockRequest request2 = new UpdateStockRequest(productoStock+1);

        //Realizamos la operación de restar stock
        mockMvc.perform(post("/api/productos/" + productoId + "/reduce-stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.Message").exists());
    }
}
