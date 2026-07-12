# Proyecto Full Stack — Estudiantes

Aplicacion de ejemplo con un CRUD de estudiantes.

- **JavaService/** — API REST con Spring Boot (Maven, Java 21, base de datos PostgreSQL).
- **Cliente/** — Frontend en Next.js (App Router, TypeScript, Tailwind) que consume la API.

> Importante: primero se levanta la API (puerto 8080) y despues el cliente (puerto 3000).

---

## 1. Backend — JavaService (Spring Boot)

Requisitos: **Java 21**, **Maven** instalados, y una instancia de **PostgreSQL**
corriendo en `localhost:5432` con la base de datos `crux` creada y el
usuario `crux_user` con permisos sobre ella.

```bash
cd JavaService
mvn spring-boot:run
```

> Nota: se asume que la base de datos `crux` ya existe en la instancia de
> Postgres (Postgres no la crea automaticamente como hacia H2 en memoria).
> Si no existe, habra que crearla manualmente (`CREATE DATABASE crux;`)
> antes de levantar la API.

La API queda disponible en `http://localhost:8080`.

### Endpoints (`/api/estudiantes`)

| Metodo | Ruta                    | Descripcion              |
|--------|-------------------------|--------------------------|
| GET    | `/api/estudiantes`      | Lista todos              |
| GET    | `/api/estudiantes/{id}` | Obtiene uno              |
| POST   | `/api/estudiantes`      | Crea (body `{ "nombre" }`) |
| PUT    | `/api/estudiantes/{id}` | Actualiza                |
| DELETE | `/api/estudiantes/{id}` | Elimina                  |

Entidad `Estudiante`: `{ "id": number, "nombre": string }`.

### Base de datos

PostgreSQL (JDBC URL: `jdbc:postgresql://localhost:5432/crux`,
usuario: `crux_user`). A diferencia del H2 en memoria anterior, los datos
ahora **persisten** entre arranques. El esquema se crea/actualiza
automaticamente via Hibernate (`spring.jpa.hibernate.ddl-auto=update`), pero
la base de datos `crux` debe existir de antemano en el servidor de
Postgres.

---

## 2. Frontend — Cliente (Next.js)

### Ejecutar localmente (sin Docker)

Requisitos: **Node.js** y **npm**.

```bash
cd Cliente
npm install      # solo la primera vez
npm run dev
```

El cliente queda disponible en `http://localhost:3000`.

La URL de la API se configura en `Cliente/.env.local`:

```
NEXT_PUBLIC_API_URL=http://localhost:8080
```

La pagina principal permite **ver, crear, editar y eliminar** estudiantes.

---

## 3. Docker

Por ahora `docker-compose.yml` (en la raiz) solo levanta el **cliente**. La API
sigue corriendo con `mvn spring-boot:run` en el host, en el puerto 8080.

```bash
docker compose up --build
```

> El flag `--build` hace que Docker revise si hubo cambios en el codigo (o en
> el `Dockerfile`) desde la ultima imagen construida, y solo reconstruye las
> capas afectadas antes de levantar los contenedores. Si no hay cambios,
> reutiliza la imagen existente en vez de reconstruir todo desde cero.

El cliente queda disponible en `http://localhost:3000` y llama a la API en
`http://localhost:8080` (definido como build arg `NEXT_PUBLIC_API_URL` en
`docker-compose.yml`, ya que Next.js incrusta esta variable en el bundle
durante el build).

El `Dockerfile` de `Cliente/` usa un build multi-stage con salida
`standalone` de Next.js para minimizar el tamano de la imagen final.

---

## Prueba rapida

Con la API corriendo:

```bash
# Crear
curl -X POST http://localhost:8080/api/estudiantes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana"}'

# Listar
curl http://localhost:8080/api/estudiantes
```
