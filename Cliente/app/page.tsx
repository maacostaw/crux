"use client";

import { useEffect, useState } from "react";
import {
  Estudiante,
  getAll,
  create,
  update,
  remove,
} from "./lib/api";

export default function Home() {
  const [estudiantes, setEstudiantes] = useState<Estudiante[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // Formulario de creacion
  const [nuevoNombre, setNuevoNombre] = useState("");

  // Estado de edicion en linea
  const [editandoId, setEditandoId] = useState<number | null>(null);
  const [editNombre, setEditNombre] = useState("");

  async function cargar() {
    setLoading(true);
    setError(null);
    try {
      setEstudiantes(await getAll());
    } catch (e) {
      setError(e instanceof Error ? e.message : "No se pudo cargar la lista");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    cargar();
  }, []);

  async function accion(fn: () => Promise<unknown>) {
    setError(null);
    try {
      await fn();
      await cargar();
    } catch (e) {
      setError(e instanceof Error ? e.message : "Ocurrio un error");
    }
  }

  async function onCrear(e: React.FormEvent) {
    e.preventDefault();
    const nombre = nuevoNombre.trim();
    if (!nombre) return;
    await accion(() => create(nombre));
    setNuevoNombre("");
  }

  function empezarEdicion(est: Estudiante) {
    setEditandoId(est.id);
    setEditNombre(est.nombre);
  }

  function cancelarEdicion() {
    setEditandoId(null);
    setEditNombre("");
  }

  async function guardarEdicion(id: number) {
    const nombre = editNombre.trim();
    if (!nombre) return;
    await accion(() => update(id, nombre));
    cancelarEdicion();
  }

  async function eliminar(id: number) {
    await accion(() => remove(id));
  }

  return (
    <main className="mx-auto max-w-2xl px-4 py-10">
      <h1 className="mb-6 text-2xl font-bold">Gestion de Estudiantes</h1>

      {/* Formulario de creacion */}
      <form onSubmit={onCrear} className="mb-6 flex gap-2">
        <input
          type="text"
          value={nuevoNombre}
          onChange={(e) => setNuevoNombre(e.target.value)}
          placeholder="Nombre del estudiante"
          className="flex-1 rounded border border-slate-300 px-3 py-2 focus:border-slate-500 focus:outline-none"
        />
        <button
          type="submit"
          className="rounded bg-slate-900 px-4 py-2 font-medium text-white hover:bg-slate-700"
        >
          Agregar
        </button>
      </form>

      {error && (
        <p className="mb-4 rounded bg-red-100 px-3 py-2 text-sm text-red-700">
          {error}
        </p>
      )}

      {loading ? (
        <p className="text-slate-500">Cargando...</p>
      ) : estudiantes.length === 0 ? (
        <p className="text-slate-500">No hay estudiantes todavia.</p>
      ) : (
        <table className="w-full overflow-hidden rounded border border-slate-200 bg-white">
          <thead className="bg-slate-100 text-left text-sm text-slate-600">
            <tr>
              <th className="w-16 px-4 py-2">ID</th>
              <th className="px-4 py-2">Nombre</th>
              <th className="w-40 px-4 py-2 text-right">Acciones</th>
            </tr>
          </thead>
          <tbody>
            {estudiantes.map((est) => (
              <tr key={est.id} className="border-t border-slate-100">
                <td className="px-4 py-2 text-slate-500">{est.id}</td>
                <td className="px-4 py-2">
                  {editandoId === est.id ? (
                    <input
                      type="text"
                      value={editNombre}
                      onChange={(e) => setEditNombre(e.target.value)}
                      className="w-full rounded border border-slate-300 px-2 py-1 focus:border-slate-500 focus:outline-none"
                      autoFocus
                    />
                  ) : (
                    est.nombre
                  )}
                </td>
                <td className="px-4 py-2">
                  <div className="flex justify-end gap-2 text-sm">
                    {editandoId === est.id ? (
                      <>
                        <button
                          onClick={() => guardarEdicion(est.id)}
                          className="rounded bg-green-600 px-3 py-1 font-medium text-white hover:bg-green-500"
                        >
                          Guardar
                        </button>
                        <button
                          onClick={cancelarEdicion}
                          className="rounded bg-slate-200 px-3 py-1 font-medium text-slate-700 hover:bg-slate-300"
                        >
                          Cancelar
                        </button>
                      </>
                    ) : (
                      <>
                        <button
                          onClick={() => empezarEdicion(est)}
                          className="rounded bg-slate-200 px-3 py-1 font-medium text-slate-700 hover:bg-slate-300"
                        >
                          Editar
                        </button>
                        <button
                          onClick={() => eliminar(est.id)}
                          className="rounded bg-red-600 px-3 py-1 font-medium text-white hover:bg-red-500"
                        >
                          Eliminar
                        </button>
                      </>
                    )}
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </main>
  );
}
