# Frontend — Cliente (Next.js)

Interfaz del CRUD de estudiantes. **Next.js** (App Router), **TypeScript**,
**Tailwind**. Consume la API de `JavaService`. Parte del monorepo; ver el
[`CLAUDE.md` raiz](../CLAUDE.md) para las generalidades y para levantar todo el
stack junto con Docker Compose.

La pagina principal permite **ver, crear, editar y eliminar** estudiantes.
El cliente queda disponible en `http://localhost:3000`.

---

## Ejecutar localmente (sin Docker)

Requisitos: **Node.js** y **npm**. Estos comandos son **independientes de
Docker**: levantan el dev server de Next con hot-reload.

```bash
npm install      # solo la primera vez
npm run dev
```

Scripts disponibles (`package.json`):

- `npm run dev` — servidor de desarrollo con hot-reload.
- `npm run build` — build de produccion (salida `standalone`).
- `npm run start` — sirve el build de produccion.
- `npm run lint` — linter.

### Configuracion de la API

La URL de la API se define en `.env.local`:

```
NEXT_PUBLIC_API_URL=http://localhost:8080
```

> Next.js incrusta las variables `NEXT_PUBLIC_*` en el bundle **en build time**.
> Si cambias esta URL, hay que reconstruir/reiniciar para que surta efecto.

---

## Ejecutar con Docker

El cliente tiene su propio `Dockerfile` (build multi-stage con la salida
`standalone` de Next.js, imagen final minima sobre `node:20-alpine`). Lo normal
es levantarlo junto con la API y Postgres via Compose (ver `CLAUDE.md` raiz),
pero tambien se puede construir suelto:

```bash
docker build -t cliente --build-arg NEXT_PUBLIC_API_URL=http://localhost:8080 .
docker run -p 3000:3000 cliente
```

> `NEXT_PUBLIC_API_URL` se pasa como **build arg** (no como variable de runtime)
> porque Next.js la incrusta en el bundle al compilar. Cambiarla implica
> reconstruir la imagen.
