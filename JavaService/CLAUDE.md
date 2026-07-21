# Backend — JavaService (contrato)

Contrato/referencia de la API. Para **comandos y como correr** ver
[`README.md`](README.md). Generalidades del proyecto en el
[`CLAUDE.md` raiz](../CLAUDE.md).

Stack: **Spring Boot**, **Maven**, **Java 21**, **PostgreSQL**. Base URL:
`http://localhost:8080`.

---

## API — `/api/estudiantes`

| Metodo | Ruta                    | Descripcion                | Body                |
|--------|-------------------------|----------------------------|---------------------|
| GET    | `/api/estudiantes`      | Lista todos                | —                   |
| GET    | `/api/estudiantes/{id}` | Obtiene uno                | —                   |
| POST   | `/api/estudiantes`      | Crea                       | `{ "nombre": str }` |
| PUT    | `/api/estudiantes/{id}` | Actualiza                  | `{ "nombre": str }` |
| DELETE | `/api/estudiantes/{id}` | Elimina                    | —                   |

### Entidad `Estudiante`

```json
{ "id": 1, "nombre": "Ana" }
```

- `id` — `number`, generado por la BD (no se envia al crear).
- `nombre` — `string`.

---

## Configuracion de la conexion a BD

Las credenciales **no** estan en `application.properties`. Precedencia (de mayor
a menor prioridad):

1. **Variables de entorno** `SPRING_DATASOURCE_URL` / `SPRING_DATASOURCE_USERNAME`
   / `SPRING_DATASOURCE_PASSWORD` — relaxed binding sobre `spring.datasource.*`.
   Es la via de `docker-compose.dev.yml` (host `postgres:5432`, red interna).
2. **`.env` local** — cargado por
   `spring.config.import=optional:file:./.env[.properties]` (Spring lo parsea
   como fichero de propiedades). Solo para ejecucion local; apunta a
   `localhost:5432`. Al ser `optional:`, si no existe la app no falla.

Es decir: dentro de Compose, las variables de entorno **sobreescriben** el
`.env`, por lo que el puerto `5432` del `.env` es irrelevante ahi.

Claves del `.env` (versionadas como plantilla en `.env.example`, el `.env` real
esta gitignoreado por contener secretos):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/crux
spring.datasource.username=crux_user
spring.datasource.password=...
```

---

## Base de datos

PostgreSQL. Los datos **persisten** entre arranques. El esquema se crea/actualiza
automaticamente via Hibernate (`spring.jpa.hibernate.ddl-auto=update`), pero la
base `crux` debe existir de antemano cuando se corre contra un Postgres del host
(el contenedor de Compose la crea solo en el primer arranque).
