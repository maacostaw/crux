# Ejercicio: Microservicios - Productos y Pedidos

Dos microservicios Spring Boot independientes, cada uno con su propia base de datos (H2 en memoria):

- **products-service** (puerto 8081): catálogo de productos y su stock.
- **orders-service** (puerto 8082): pedidos. Ya tiene implementado **crear pedido**, que llama a products-service para validar stock y descontarlo.

No necesitas Docker para esto: son dos apps Spring Boot normales. Solo necesitas **Java 17+** y **Maven** (o usar el `mvnw` si lo agregas). Docker quedaría para una vuelta futura si quieres containerizar todo con docker-compose, pero no es necesario para este ejercicio.

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
# Ver productos (ya vienen sembrados 2 al arrancar)
curl http://localhost:8081/products

# Crear un pedido
curl -X POST http://localhost:8082/orders \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 2}'
```

Si el pedido se creó bien, el stock del producto en `products-service` debe haber bajado.

## 2. Tu tarea: implementar "cancelar pedido"

Vas a implementar un endpoint `POST /orders/{id}/cancel` en **orders-service**.

### Reglas del negocio

1. Si el pedido no existe → responder **404**.
2. Si el pedido ya está `CANCELLED` → responder **409** (no se puede cancelar dos veces).
3. Si el pedido está `CREATED` → hay que:
   - Avisarle a `products-service` que **devuelva el stock** que se descontó (vas a necesitar crear un endpoint nuevo en products-service para esto, ej. `POST /products/{id}/restore-stock`, similar al que ya existe para descontar).
   - Cambiar el estado del pedido a `CANCELLED` en la base de datos de orders-service.
4. **El punto importante:** ¿qué pasa si `products-service` no responde o falla al intentar devolver el stock? Decide y justifica qué hace tu sistema en ese caso (¿el pedido queda cancelado igual? ¿queda en un estado intermedio? ¿reintenta?). No hay una única respuesta correcta, pero se espera que lo pienses explícitamente, no que lo ignores.
5. Agrega logging con algo que permita rastrear la operación (mínimo: log al iniciar la cancelación, log si products-service falla, log al terminar).
6. Bonus si le agregas una prueba de integración al flujo de cancelación.

Cuando quieras, ven y hablamos de tu solución, o pregúntame dudas puntuales sobre alguna regla.
