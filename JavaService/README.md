# Backend — JavaService (Spring Boot)

API REST del CRUD de estudiantes. **Spring Boot**, **Maven**, **Java 21**,
**PostgreSQL**. Queda disponible en `http://localhost:8080`.

Este README cubre **como correrlo**. El contrato del servicio (endpoints,
entidad, variables de entorno, BD) esta en [`CLAUDE.md`](CLAUDE.md).

---

## Ejecutar localmente (sin Docker)

Requisitos: **Java 21**, **Maven**, y un **PostgreSQL** con la base `crux`
creada y el usuario `crux_user` con permisos.

Las credenciales de la BD van en un `.env` (dev, fuera de git). Crealo desde la
plantilla y arranca:

```bash
cp .env.example .env   # y ajusta la password real
mvn spring-boot:run
```

El `.env` es **solo para ejecucion local** y apunta a un Postgres nativo del
host en el puerto **5432**. `application.properties` lo carga via
`spring.config.import` (ver contrato en `CLAUDE.md`).

---

## Ejecutar con Docker (suelto)

Lo normal es levantar la API junto con Postgres y el cliente via Compose (ver
[`README.md` raiz](../README.md)). Para construir/correr solo la API:

```bash
docker build -t javaservice .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/crux \
  -e SPRING_DATASOURCE_USERNAME=crux_user \
  -e SPRING_DATASOURCE_PASSWORD=... \
  javaservice
```

En Docker las variables `SPRING_DATASOURCE_*` inyectan la conexion y
sobreescriben el `.env` local (ver `CLAUDE.md`).
