# Frontend — Cliente (contrato)

Contrato/referencia del cliente. Para **comandos y como correr** ver
[`README.md`](README.md). Generalidades del proyecto en el
[`CLAUDE.md` raiz](../CLAUDE.md).

Stack: **Next.js** (App Router), **TypeScript**, **Tailwind**. Base URL:
`http://localhost:3000`.

---

## Funcionalidad

La pagina principal es un CRUD de estudiantes: permite **ver, crear, editar y
eliminar**. Todas las operaciones se hacen contra la API de `JavaService`
(recurso `/api/estudiantes`).

---

## Consumo de la API

- La URL base de la API se resuelve con **`NEXT_PUBLIC_API_URL`**.
- Contrato de los endpoints y la entidad `Estudiante`: ver
  [`JavaService/CLAUDE.md`](../JavaService/CLAUDE.md).

### `NEXT_PUBLIC_API_URL` — comportamiento build-time

- Es una variable `NEXT_PUBLIC_*`: Next.js la **incrusta en el bundle en build
  time**, no se lee en runtime. Cambiarla exige **reconstruir/reiniciar**.
- En ejecucion local se define en `.env.local`:

  ```
  NEXT_PUBLIC_API_URL=http://localhost:8080
  ```

- En Docker se pasa como **build arg** con el mismo nombre (ver `README.md` y el
  `Dockerfile`), porque debe estar presente durante `npm run build`.
