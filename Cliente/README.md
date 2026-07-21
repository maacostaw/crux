# Frontend — Cliente (Next.js)

Interfaz del CRUD de estudiantes. **Next.js** (App Router), **TypeScript**,
**Tailwind**. Consume la API de `JavaService`. Queda disponible en
`http://localhost:3000`.

Este README cubre **como correrlo**. El contrato del servicio (variables,
consumo de la API, comportamiento build-time) esta en [`CLAUDE.md`](CLAUDE.md).

---

## Ejecutar localmente (sin Docker)

Requisitos: **Node.js** y **npm**. Levanta el dev server de Next con hot-reload:

```bash
npm install      # solo la primera vez
npm run dev
```

Scripts (`package.json`):

- `npm run dev` — servidor de desarrollo con hot-reload.
- `npm run build` — build de produccion (salida `standalone`).
- `npm run start` — sirve el build de produccion.
- `npm run lint` — linter.

La URL de la API se define en `.env.local` (`NEXT_PUBLIC_API_URL`); ver el
contrato en `CLAUDE.md`.

---

## Ejecutar con Docker (suelto)

Lo normal es levantarlo via Compose junto con la API y Postgres (ver
[`README.md` raiz](../README.md)). Para construir/correr solo el cliente:

```bash
docker build -t cliente --build-arg NEXT_PUBLIC_API_URL=http://localhost:8080 .
docker run -p 3000:3000 cliente
```

`NEXT_PUBLIC_API_URL` se pasa como **build arg** (no runtime): Next.js la
incrusta en el bundle al compilar. Cambiarla implica reconstruir la imagen.
