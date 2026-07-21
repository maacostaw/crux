# Proyecto Full Stack — Estudiantes

Aplicacion de ejemplo con un CRUD de estudiantes. Monorepo con dos partes
independientes mas la orquestacion Docker:

- **JavaService/** — API REST con Spring Boot (Maven, Java 21, PostgreSQL).
  Como correrla: [`JavaService/README.md`](JavaService/README.md).
- **Cliente/** — Frontend en Next.js (App Router, TypeScript, Tailwind).
  Como correrlo: [`Cliente/README.md`](Cliente/README.md).

> El "contrato" de cada parte (APIs, entidad, variables de entorno,
> arquitectura) esta en los `CLAUDE.md`: uno en la raiz para el proyecto y uno
> en cada carpeta para su servicio.

---

## Levantar todo el stack — Docker Compose (dev)

`docker-compose.dev.yml` levanta **todo el stack de desarrollo** (PostgreSQL +
API + cliente). No hace falta tener Java, Maven, Node ni Postgres instalados en
el host.

```bash
docker compose -f docker-compose.dev.yml up --build
```

- **cliente** → `http://localhost:3000`
- **API** → `http://localhost:8080`
- **postgres** → publicado en el host en `5433` (internamente `5432`)

> `--build` reconstruye solo las capas afectadas por cambios; si no hay cambios,
> reutiliza las imagenes existentes. El contenedor de Postgres crea la base
> `crux` en el primer arranque.

Para correr cada parte por separado (sin Docker, con hot-reload), ver el
`README.md` de cada carpeta.

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
