# Ejercicio: Microservicios - Productos y Pedidos

Dos microservicios Spring Boot independientes, cada uno con su propia base de datos (H2 en memoria):

- **products-service** (puerto 8081): catálogo de productos y su stock.
- **orders-service** (puerto 8082): pedidos. Ya tiene implementado **crear pedido**, que llama a products-service para validar stock y descontarlo.

Solo se necesita **Java 17+** y **Maven**

## 1. Cómo ejecutar

En dos terminales distintas:

```bash
# Terminal 1
cd products-service
mvn spring-boot:run
```

```bash
# Terminal 2
cd orders-service
mvn spring-boot:run
```

Prueba que funciona (con curl o Postman):

```bash
# Ver productos
curl http://localhost:8081/products

# Crear un pedido
curl -X POST http://localhost:8082/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 2}'

# Cancelar un pedido
curl -X GET http://localhost:8082/api/pedidos/{id}/cancelar-pedido \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 2}'
```

Si el pedido se creó bien, el stock del producto en `products-service` debe haber bajado.

Si el pedido se canceló correctamente el stock del producto en `products-service` debe haber sido restaurado.

No se puede cancelar un pedido 2 veces.