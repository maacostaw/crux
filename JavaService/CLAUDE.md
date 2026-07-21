# Backend — JavaService (Spring Boot)

API REST del CRUD de estudiantes. **Spring Boot**, **Maven**, **Java 21**,
base de datos **PostgreSQL**. Parte del monorepo; ver el
[`CLAUDE.md` raiz](../CLAUDE.md) para las generalidades y para levantar todo el
stack junto con Docker Compose.

La API queda disponible en `http://localhost:8080`.

---

## Ejecutar localmente (sin Docker)

Requisitos: **Java 21**, **Maven**, y una instancia de **PostgreSQL** con la
base `crux` creada y el usuario `crux_user` con permisos sobre ella.

Las credenciales de la BD **no** estan en `application.properties`: van en un
fichero `.env` (especifico de dev, fuera de git). Crealo la primera vez desde la
plantilla y arranca:

```bash
cp .env.example .env   # y ajusta la password real
mvn spring-boot:run
```

### El `.env` de dev

`application.properties` lo carga con
`spring.config.import=optional:file:./.env[.properties]` (Spring lo parsea como
fichero de propiedades). Al ser `optional:`, si el `.env` no existe la app no
falla — ese es el caso de Docker, donde las credenciales llegan por variables de
entorno (ver mas abajo).

Este `.env` es **solo para ejecucion local** y apunta a un Postgres nativo del
host en el puerto **5432**:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/crux
spring.datasource.username=crux_user
spring.datasource.password=...
```

> El `.env` esta gitignoreado (contiene secretos). En el repo solo se versiona
> `.env.example` como plantilla.

---

## Ejecutar con Docker

La API tiene su propio `Dockerfile` (build multi-stage: compila el jar con
Maven/JDK 21 y lo ejecuta sobre `eclipse-temurin:21-jre-alpine`). Lo normal es
levantarla junto con Postgres y el cliente via Compose (ver `CLAUDE.md` raiz),
pero tambien se puede construir suelta:

```bash
docker build -t javaservice .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/crux \
  -e SPRING_DATASOURCE_USERNAME=crux_user \
  -e SPRING_DATASOURCE_PASSWORD=... \
  javaservice
```

La conexion a la BD se sobreescribe con variables de entorno (Spring las mapea
sobre `spring.datasource.*` por relaxed binding), sin tocar
`application.properties` **ni** el `.env`:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

Estas variables tienen prioridad sobre el `.env` local y son la via que usa
`docker-compose.dev.yml` para inyectar la conexion (host `postgres:5432` por la
red interna). Por eso el `.env` local (5432) es irrelevante dentro del stack de
Compose: queda sobreescrito.

---

## Endpoints (`/api/estudiantes`)

| Metodo | Ruta                    | Descripcion                |
|--------|-------------------------|----------------------------|
| GET    | `/api/estudiantes`      | Lista todos                |
| GET    | `/api/estudiantes/{id}` | Obtiene uno                |
| POST   | `/api/estudiantes`      | Crea (body `{ "nombre" }`) |
| PUT    | `/api/estudiantes/{id}` | Actualiza                  |
| DELETE | `/api/estudiantes/{id}` | Elimina                    |

Entidad `Estudiante`: `{ "id": number, "nombre": string }`.

---

## Base de datos

PostgreSQL. En ejecucion local la JDBC URL es
`jdbc:postgresql://localhost:5432/crux` (usuario `crux_user`); en Compose la
inyecta el entorno apuntando a `postgres:5432`. Los datos **persisten** entre
arranques. El esquema se crea/actualiza automaticamente via Hibernate
(`spring.jpa.hibernate.ddl-auto=update`), pero la base `crux` debe existir de
antemano cuando corres contra un Postgres del host (el contenedor de Compose la
crea solo).
