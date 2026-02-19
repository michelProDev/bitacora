import React, { useEffect, useMemo, useState } from 'react';
import { ENV } from '../api/env.js';
import { httpJson } from '../api/http.js';

export default function ClientesPage() {
  const baseUrl = ENV.CRM_BASE_URL;
  const catalogoUrl = ENV.CATALOGO_BASE_URL;
  const api = useMemo(() => ({
    list: () => httpJson(`${baseUrl}/api/clientes`),
    get: (id) => httpJson(`${baseUrl}/api/clientes/${id}`),
    create: (payload) => httpJson(`${baseUrl}/api/clientes`, { method: 'POST', body: JSON.stringify(payload) }),
    updateByNombre: (nombre, payload) => httpJson(`${baseUrl}/api/clientes/nombre/${encodeURIComponent(nombre)}`, { method: 'PUT', body: JSON.stringify(payload) }),
    removeByNombre: (nombre) => httpJson(`${baseUrl}/api/clientes/nombre/${encodeURIComponent(nombre)}`, { method: 'DELETE' }),
    listPaises: () => httpJson(`${catalogoUrl}/api/paises`),
    buscar: (nombre) => httpJson(`${baseUrl}/api/clientes/buscar?nombre=${encodeURIComponent(nombre)}`),
  }), [baseUrl, catalogoUrl]);

  const [items, setItems] = useState([]);
  const [paises, setPaises] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const [form, setForm] = useState({ nombre: '', paisId: '' });
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
    loadPaises();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  async function loadPaises() {
    try {
      const data = await api.listPaises();
      setPaises(Array.isArray(data) ? data : []);
    } catch (e) {
      console.error('Error loading paises', e);
    }
  }

  async function onCreate() {
    setError('');
    try {
      await api.create({ nombre: form.nombre, paisId: form.paisId ? Number(form.paisId) : null });
      setForm({ nombre: '', paisId: '' });
      await refresh();
    } catch (e) {
      setError(`${e.message}${e.body ? ` - ${JSON.stringify(e.body)}` : ''}`);
    }
  }

  async function onUpdate() {
    setError('');
    try {
      await api.updateByNombre(nombreForOps, { nombre: form.nombre, paisId: form.paisId ? Number(form.paisId) : null });
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
        <h1 className="h1">Clientes</h1>
        <span className="muted">{baseUrl}</span>
      </div>

      {error ? <div className="alert error" style={{ marginBottom: 12 }}>{error}</div> : null}

      <div className="row" style={{ marginBottom: 12 }}>
        <div className="field" style={{ gridColumn: 'span 4' }}>
          <label>Nombre</label>
          <input value={form.nombre} onChange={(e) => setForm((f) => ({ ...f, nombre: e.target.value }))} />
        </div>
        <div className="field" style={{ gridColumn: 'span 4' }}>
          <label>País</label>
          <select value={form.paisId} onChange={(e) => setForm((f) => ({ ...f, paisId: e.target.value }))}>
            <option value="">-- Seleccionar --</option>
            {paises.map((p, i) => (
              <option key={i} value={i + 1}>{p?.nombre ?? ''}</option>
            ))}
          </select>
        </div>
        <div style={{ gridColumn: 'span 4', display: 'flex', alignItems: 'flex-end', gap: 10 }}>
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
            <th>País</th>
          </tr>
        </thead>
        <tbody>
          {items.map((c, idx) => (
            <tr key={idx}>
              <td>{idx + 1}</td>
              <td>{c?.nombre ?? ''}</td>
              <td>{c?.paisNombre ?? ''}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
