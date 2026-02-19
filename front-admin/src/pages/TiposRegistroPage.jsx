import React, { useEffect, useMemo, useState } from 'react';
import { ENV } from '../api/env.js';
import { httpJson } from '../api/http.js';

export default function TiposRegistroPage() {
  const baseUrl = ENV.CATALOGO_BASE_URL;
  const api = useMemo(() => ({
    list: () => httpJson(`${baseUrl}/api/tipos-registro`),
    get: (id) => httpJson(`${baseUrl}/api/tipos-registro/${id}`),
    create: (payload) => httpJson(`${baseUrl}/api/tipos-registro`, { method: 'POST', body: JSON.stringify(payload) }),
    updateByNombre: (nombre, payload) => httpJson(`${baseUrl}/api/tipos-registro/nombre/${encodeURIComponent(nombre)}`, { method: 'PUT', body: JSON.stringify(payload) }),
    removeByNombre: (nombre) => httpJson(`${baseUrl}/api/tipos-registro/nombre/${encodeURIComponent(nombre)}`, { method: 'DELETE' }),
    buscar: (nombre) => httpJson(`${baseUrl}/api/tipos-registro/buscar?nombre=${encodeURIComponent(nombre)}`),
  }), [baseUrl]);

  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const [form, setForm] = useState({ nombre: '' });
  const [nombreForOps, setNombreForOps] = useState('');
  const [searchNombre, setSearchNombre] = useState('');

  async function refresh() {
    setLoading(true);
    setError('');
    try {
      const data = await api.list();
      setItems(Array.isArray(data) ? data : []);
    } catch (e) {
      setError(`${e.message}${e.body ? ` - ${JSON.stringify(e.body)}` : ''}`);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    refresh();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  async function onCreate() {
    setError('');
    try {
      await api.create(form);
      setForm({ nombre: '' });
      await refresh();
    } catch (e) {
      setError(`${e.message}${e.body ? ` - ${JSON.stringify(e.body)}` : ''}`);
    }
  }

  async function onUpdate() {
    setError('');
    try {
      await api.updateByNombre(nombreForOps, form);
      await refresh();
    } catch (e) {
      setError(`${e.message}${e.body ? ` - ${JSON.stringify(e.body)}` : ''}`);
    }
  }

  async function onDelete() {
    setError('');
    try {
      await api.removeByNombre(nombreForOps);
      await refresh();
    } catch (e) {
      setError(`${e.message}${e.body ? ` - ${JSON.stringify(e.body)}` : ''}`);
    }
  }

  async function onSearch() {
    if (!searchNombre.trim()) { await refresh(); return; }
    setLoading(true);
    setError('');
    try {
      const data = await api.buscar(searchNombre.trim());
      setItems(data ? [data] : []);
    } catch (e) {
      if (e.message?.includes('404')) { setItems([]); }
      else { setError(`${e.message}${e.body ? ` - ${JSON.stringify(e.body)}` : ''}`); }
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="card">
      <div className="headerRow">
        <h1 className="h1">Tipos de Registro</h1>
        <span className="muted">{baseUrl}</span>
      </div>

      {error ? <div className="alert error" style={{ marginBottom: 12 }}>{error}</div> : null}

      <div className="row" style={{ marginBottom: 12 }}>
        <div className="field" style={{ gridColumn: 'span 9' }}>
          <label>Nombre</label>
          <input value={form.nombre} onChange={(e) => setForm((f) => ({ ...f, nombre: e.target.value }))} />
        </div>
        <div style={{ gridColumn: 'span 3', display: 'flex', alignItems: 'flex-end', gap: 10 }}>
          <button className="primary" onClick={onCreate}>Create</button>
          <button onClick={refresh} disabled={loading}>{loading ? 'Loading...' : 'Refresh'}</button>
        </div>
      </div>

      <div className="row" style={{ marginBottom: 12 }}>
        <div className="field" style={{ gridColumn: 'span 6' }}>
          <label>Nombre (para actualizar/eliminar)</label>
          <input value={nombreForOps} onChange={(e) => setNombreForOps(e.target.value)} placeholder="Nombre exacto" />
        </div>
        <div style={{ gridColumn: 'span 6', display: 'flex', alignItems: 'flex-end', gap: 10 }}>
          <button onClick={onUpdate} disabled={!nombreForOps}>Update</button>
          <button className="danger" onClick={onDelete} disabled={!nombreForOps}>Delete</button>
        </div>
      </div>

      <div className="row" style={{ marginBottom: 12 }}>
        <div className="field" style={{ gridColumn: 'span 9' }}>
          <label>Buscar por nombre</label>
          <input value={searchNombre} onChange={(e) => setSearchNombre(e.target.value)} placeholder="Nombre exacto" />
        </div>
        <div className="field" style={{ gridColumn: 'span 3', display: 'flex', alignItems: 'flex-end' }}>
          <button onClick={onSearch} disabled={loading}>Buscar</button>
        </div>
      </div>

      <table className="table">
        <thead>
          <tr>
            <th>#</th>
            <th>Nombre</th>
          </tr>
        </thead>
        <tbody>
          {items.map((c, idx) => (
            <tr key={idx}>
              <td>{idx + 1}</td>
              <td>{c?.nombre ?? ''}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
