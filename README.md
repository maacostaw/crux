# Ejercicio: Microservicios - Productos y Pedidos

Dos microservicios Spring Boot independientes, cada uno con su propia base de datos (H2 en memoria):

- **products-service** (puerto 8081): catálogo de productos y su stock.
- **orders-service** (puerto 8082): pedidos. Ya tiene implementado **crear pedido**, que llama a products-service para validar stock y descontarlo.

Solo se necesita **Java 17+** y **Maven**

## 1. Cómo ejecutar

1) Para correr kafka y su UI

```bash
docker compose up -d
```

2) Dale permisos de ejecución al script (solo la primera vez):

```bash
chmod +x kafka-setup.sh
```

3) Crea el topic `pedido-cancelado` (si no existe) y las 3 ACLs necesarias

```bash
./kafka-setup.sh
```

Esto 

4) Luego ya podemos correr los servicios en dos terminales distintas:

```bash
# Terminal 1
cd productos-service
mvn spring-boot:run
```

```bash
# Terminal 2
cd pedidos-service
mvn spring-boot:run
```

Prueba que funciona (con curl o Postman):

```bash
# Ver productos
curl http://localhost:8081/productos

# Crear un pedido
curl -X POST http://localhost:8082/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 2}'

# Cancelar un pedido
curl -X GET http://localhost:8082/api/pedidos/{id}/cancelar-pedido \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 2}'
```

Si el pedido se creó bien, el stock del producto en `productos-service` debe haber bajado.

Si el pedido se canceló correctamente el stock del producto en `productos-service` debe haber sido restaurado.

No se puede cancelar un pedido 2 veces.