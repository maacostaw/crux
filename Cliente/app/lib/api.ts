export interface Estudiante {
  id: number;
  nombre: string;
}

const API_URL = process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080";
const BASE = `${API_URL}/api/estudiantes`;

async function handle<T>(res: Response): Promise<T> {
  if (!res.ok) {
    throw new Error(`Error ${res.status}: ${res.statusText}`);
  }
  // 204 No Content no trae cuerpo
  if (res.status === 204) {
    return undefined as T;
  }
  return res.json() as Promise<T>;
}

export async function getAll(): Promise<Estudiante[]> {
  return handle<Estudiante[]>(await fetch(BASE, { cache: "no-store" }));
}

export async function create(nombre: string): Promise<Estudiante> {
  return handle<Estudiante>(
    await fetch(BASE, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nombre }),
    })
  );
}

export async function update(id: number, nombre: string): Promise<Estudiante> {
  return handle<Estudiante>(
    await fetch(`${BASE}/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nombre }),
    })
  );
}

export async function remove(id: number): Promise<void> {
  return handle<void>(await fetch(`${BASE}/${id}`, { method: "DELETE" }));
}
