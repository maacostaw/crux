# Proyecto Full Stack — Estudiantes

Aplicacion de ejemplo con un CRUD de estudiantes. Monorepo con dos partes
independientes mas la orquestacion Docker:

- **JavaService/** — API REST con Spring Boot (Maven, Java 21, PostgreSQL).
  Detalles y comandos propios en [`JavaService/CLAUDE.md`](JavaService/CLAUDE.md).
- **Cliente/** — Frontend en Next.js (App Router, TypeScript, Tailwind) que
  consume la API. Detalles y comandos propios en
  [`Cliente/CLAUDE.md`](Cliente/CLAUDE.md).

Cada carpeta tiene su propio `CLAUDE.md` con como correrla de forma
**independiente** (sin Docker y en Docker suelto). Este documento cubre solo
las **generalidades** y como levantar **todo el stack junto** con Docker
Compose.

---

## Arquitectura

```
Cliente (Next.js, :3000)  ->  JavaService (Spring Boot, :8080)  ->  PostgreSQL (:5432)
```

- El **cliente** llama a la API por HTTP. La URL de la API se resuelve en build
  time (`NEXT_PUBLIC_API_URL`), porque Next.js incrusta esa variable en el
  bundle.
- La **API** expone `/api/estudiantes` (CRUD) y persiste en **PostgreSQL**
  (esquema autogestionado por Hibernate, `ddl-auto=update`).
- La entidad es `Estudiante`: `{ "id": number, "nombre": string }`.

Orden de arranque en ejecucion manual: primero la API (`:8080`), luego el
cliente (`:3000`). Con Docker Compose el orden lo garantiza `depends_on`.

---

## Levantar todo el stack — Docker Compose (dev)

`docker-compose.dev.yml` (en la raiz) levanta **todo el stack de desarrollo**:
base de datos **PostgreSQL**, la **API** (JavaService) y el **cliente**
(Next.js). No hace falta tener Java, Maven, Node ni Postgres instalados en el
host. (Para produccion habra un compose aparte; este es solo dev.)

```bash
docker compose -f docker-compose.dev.yml up --build
```

> El flag `--build` reconstruye solo las capas afectadas por cambios en el
> codigo o los `Dockerfile`; si no hay cambios, reutiliza las imagenes
> existentes.

Servicios y orden de arranque:

- **postgres** — imagen `postgres:16-alpine`. Crea automaticamente la base
  `crux` y el usuario `crux_user` (variables `POSTGRES_*`). Los datos persisten
  en el volumen `postgres_data`. Se publica en el host como **`5433:5432`**:
  internamente el contenedor escucha en `5432`, pero se expone en `5433` para
  no chocar con un Postgres local en el `5432` del host.
- **javaservice** — la API en `http://localhost:8080`. Espera con `depends_on`
  a que Postgres este *healthy* (healthcheck con `pg_isready`) y se conecta a la
  BD por el nombre de servicio `postgres:5432` (red interna, no `localhost`)
  via las variables `SPRING_DATASOURCE_*`. Estas variables **sobreescriben**
  cualquier `.env` local del backend.
- **cliente** — el frontend en `http://localhost:3000`. Llama a la API en
  `http://localhost:8080` (build arg `NEXT_PUBLIC_API_URL`).

> Nota: con el contenedor de Postgres no hace falta crear la base `crux` a mano;
> el contenedor la crea en el primer arranque. Si en cambio corres cada parte a
> mano (ver los `CLAUDE.md` de cada carpeta), aplican sus propios requisitos.

Ambos `Dockerfile` usan builds multi-stage para minimizar la imagen final:
`Cliente/` con la salida `standalone` de Next.js, y `JavaService/` compilando
el jar con Maven/JDK 21 y ejecutandolo sobre `eclipse-temurin:21-jre-alpine`.

---

## Prueba rapida

Con la API corriendo (via Compose o a mano):

```bash
# Crear
curl -X POST http://localhost:8080/api/estudiantes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana"}'

# Listar
curl http://localhost:8080/api/estudiantes
```
