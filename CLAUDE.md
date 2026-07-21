# Proyecto Full Stack — Estudiantes (contrato)

Documento de **contrato/referencia** del proyecto: arquitectura, topologia de
servicios y los contratos que cruzan de un servicio a otro. Para **comandos y
como correr** ver [`README.md`](README.md). El contrato especifico de cada
servicio esta en su carpeta:

- Backend: [`JavaService/CLAUDE.md`](JavaService/CLAUDE.md)
- Frontend: [`Cliente/CLAUDE.md`](Cliente/CLAUDE.md)

---

## Arquitectura

```
Cliente (Next.js, :3000)  ->  JavaService (Spring Boot, :8080)  ->  PostgreSQL (:5432)
```

- El **cliente** llama a la API por HTTP.
- La **API** expone `/api/estudiantes` (CRUD) y persiste en **PostgreSQL**
  (esquema autogestionado por Hibernate, `ddl-auto=update`).
- Orden de arranque manual: primero la API (`:8080`), luego el cliente
  (`:3000`). En Compose lo garantiza `depends_on` + healthcheck de Postgres.

---

## Modelo de dominio

Entidad **`Estudiante`**, unico recurso del sistema:

```json
{ "id": 1, "nombre": "Ana" }
```

- `id` — `number`, generado por la BD.
- `nombre` — `string`.

---

## Contratos entre servicios

### Cliente → API

- El cliente resuelve la URL de la API con `NEXT_PUBLIC_API_URL`, incrustada en
  **build time** (Next.js la mete en el bundle al compilar; cambiarla exige
  reconstruir). Detalle en `Cliente/CLAUDE.md`.
- Contrato de la API que consume: endpoints `/api/estudiantes` (ver
  `JavaService/CLAUDE.md`).

### API → PostgreSQL

- La conexion se configura con `SPRING_DATASOURCE_URL` /
  `SPRING_DATASOURCE_USERNAME` / `SPRING_DATASOURCE_PASSWORD` (relaxed binding
  sobre `spring.datasource.*`). Estas variables **tienen prioridad** sobre el
  `.env` local del backend. Detalle en `JavaService/CLAUDE.md`.

---

## Topologia de puertos

| Servicio    | Puerto host | Puerto interno | Notas                                   |
|-------------|-------------|----------------|-----------------------------------------|
| cliente     | 3000        | 3000           | Next.js                                 |
| javaservice | 8080        | 8080           | Spring Boot                             |
| postgres    | **5433**    | 5432           | 5433 en host para no chocar con el local|

En ejecucion **local** (sin Docker), el backend apunta por defecto a un Postgres
nativo del host en `5432` (ver su `.env`). El `5433` es solo la publicacion del
contenedor de Compose.

---

## Entornos

- **dev** — `docker-compose.dev.yml` (raiz) + `.env` locales por servicio.
- **prod** — pendiente: habra un compose y unos `.env` de produccion aparte.
