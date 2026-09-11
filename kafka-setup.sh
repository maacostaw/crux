#!/bin/bash
set -e

source .env

echo "Creando archivo de credenciales admin temporal..."
docker exec crux-kafka bash -c "cat <<EOF > /tmp/admin-client.properties
security.protocol=SASL_PLAINTEXT
sasl.mechanism=PLAIN
sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required username=\"admin\" password=\"${KAFKA_ADMIN_PASSWORD}\";
EOF"

echo "Creando topic pedidos.pedido-cancelado..."
docker exec crux-kafka /opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --command-config /tmp/admin-client.properties \
  --create --if-not-exists \
  --topic pedidos.pedido-cancelado \
  --partitions 3 \
  --replication-factor 1

echo "Creando ACL: pedidosservice puede escribir en pedidos.pedido-cancelado..."
docker exec crux-kafka /opt/kafka/bin/kafka-acls.sh \
  --bootstrap-server localhost:9092 \
  --command-config /tmp/admin-client.properties \
  --add --allow-principal User:pedidosservice \
  --operation Write --topic pedidos.pedido-cancelado

echo "Creando ACL: productosservice puede leer de pedidos.pedido-cancelado..."
docker exec crux-kafka /opt/kafka/bin/kafka-acls.sh \
  --bootstrap-server localhost:9092 \
  --command-config /tmp/admin-client.properties \
  --add --allow-principal User:productosservice \
  --operation Read --topic pedidos.pedido-cancelado

echo "Creando ACL: productosservice puede usar su consumer group..."
docker exec crux-kafka /opt/kafka/bin/kafka-acls.sh \
  --bootstrap-server localhost:9092 \
  --command-config /tmp/admin-client.properties \
  --add --allow-principal User:productosservice \
  --operation Read --group productos
  
echo "Creando topic productos.stock-devuelto..."
docker exec crux-kafka /opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --command-config /tmp/admin-client.properties \
  --create --if-not-exists \
  --topic productos.stock-devuelto \
  --partitions 3 \
  --replication-factor 1

echo "Creando ACL: productosservice puede escribir en productos.stock-devuelto..."
docker exec crux-kafka /opt/kafka/bin/kafka-acls.sh \
  --bootstrap-server localhost:9092 \
  --command-config /tmp/admin-client.properties \
  --add --allow-principal User:productosservice \
  --operation Write --topic productos.stock-devuelto

echo "Creando ACL: pedidosservice puede leer de productos.stock-devuelto..."
docker exec crux-kafka /opt/kafka/bin/kafka-acls.sh \
  --bootstrap-server localhost:9092 \
  --command-config /tmp/admin-client.properties \
  --add --allow-principal User:pedidosservice \
  --operation Read --topic productos.stock-devuelto

echo "Creando ACL: pedidosservice puede usar su consumer group..."
docker exec crux-kafka /opt/kafka/bin/kafka-acls.sh \
  --bootstrap-server localhost:9092 \
  --command-config /tmp/admin-client.properties \
  --add --allow-principal User:pedidosservice \
  --operation Read --group pedidos

echo "Listo. Kafka configurado."