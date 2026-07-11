# Proyecto Full Stack — Estudiantes

Aplicacion de ejemplo con un CRUD de estudiantes.

- **JavaService/** — API REST con Spring Boot (Maven, Java 21, base de datos H2 en memoria).
- **Cliente/** — Frontend en Next.js (App Router, TypeScript, Tailwind) que consume la API.

> Importante: primero se levanta la API (puerto 8080) y despues el cliente (puerto 3000).

---

## 1. Backend — JavaService (Spring Boot)

Requisitos: **Java 21** y **Maven** instalados.

```bash
cd JavaService
mvn spring-boot:run
```

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

### Consola H2

Disponible en `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:mem:estudiantes`, usuario: `sa`, sin contrasena).
Los datos viven en memoria y se reinician en cada arranque.

---

## 2. Frontend — Cliente (Next.js)

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
