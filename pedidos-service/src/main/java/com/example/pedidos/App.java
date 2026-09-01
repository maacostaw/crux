package com.example.pedidos;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    // RestTemplate: el cliente HTTP que usamos para hablar con products-service.
    // En un caso real le pondrias timeouts explicitos (connect/read timeout).
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
