import React, { useEffect, useMemo, useState } from 'react';
import { ENV } from '../api/env.js';
import { httpJson } from '../api/http.js';

export default function RecursosPage() {
  const baseUrl = ENV.HR_BASE_URL;
  const catalogoUrl = ENV.CATALOGO_BASE_URL;
  const api = useMemo(() => ({
    list: () => httpJson(`${baseUrl}/api/recursos`),
    get: (id) => httpJson(`${baseUrl}/api/recursos/${id}`),
    create: (payload) => httpJson(`${baseUrl}/api/recursos`, { method: 'POST', body: JSON.stringify(payload) }),
    updateByNombre: (nombre, payload) => httpJson(`${baseUrl}/api/recursos/nombre/${encodeURIComponent(nombre)}`, { method: 'PUT', body: JSON.stringify(payload) }),
    removeByNombre: (nombre) => httpJson(`${baseUrl}/api/recursos/nombre/${encodeURIComponent(nombre)}`, { method: 'DELETE' }),
    listCategorias: () => httpJson(`${catalogoUrl}/api/categorias`),
    buscar: (nombre) => httpJson(`${baseUrl}/api/recursos/buscar?nombre=${encodeURIComponent(nombre)}`),
  }), [baseUrl, catalogoUrl]);

  const emptyForm = { nombre: '', apellidos: '', categoriaId: '', tecnologia: '', correo: '', password: '', rol: '' };

  const [items, setItems] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [form, setForm] = useState(emptyForm);
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
    loadCategorias();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  async function loadCategorias() {
    try {
      const data = await api.listCategorias();
      setCategorias(Array.isArray(data) ? data : []);
    } catch (e) {
      console.error('Error loading categorias', e);
    }
  }

  async function onCreate() {
    setError('');
    try {
      await api.create({ ...form, categoriaId: form.categoriaId ? Number(form.categoriaId) : null, rol: form.rol || 'USUARIO' });
      setForm(emptyForm);
      await refresh();
    } catch (e) {
      setError(`${e.message}${e.body ? ` - ${JSON.stringify(e.body)}` : ''}`);
    }
  }

  async function onUpdate() {
    setError('');
    try {
      await api.updateByNombre(nombreForOps, { ...form, categoriaId: form.categoriaId ? Number(form.categoriaId) : null, password: form.password || undefined });
      await refresh();
    } catch (e) {
      setError(`${e.message}${e.body ? ` - ${JSON.stringify(e.body)}` : ''}`);
    }
  }

  async function onDelete() {
    setError('');
    try {
      await api.removeByNombre(nombreForOps);
      setNombreForOps('');
      setForm(emptyForm);
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
        <h1 className="h1">Recursos</h1>
        <span className="muted">{baseUrl}</span>
      </div>

      {error ? <div className="alert error" style={{ marginBottom: 12 }}>{error}</div> : null}

      <div className="row" style={{ marginBottom: 12 }}>
        <div className="field" style={{ gridColumn: 'span 3' }}>
          <label>Nombre</label>
          <input value={form.nombre} onChange={(e) => setForm((f) => ({ ...f, nombre: e.target.value }))} />
        </div>
        <div className="field" style={{ gridColumn: 'span 3' }}>
          <label>Apellidos</label>
          <input value={form.apellidos} onChange={(e) => setForm((f) => ({ ...f, apellidos: e.target.value }))} />
        </div>
        <div className="field" style={{ gridColumn: 'span 3' }}>
          <label>Categoría</label>
          <select value={form.categoriaId} onChange={(e) => setForm((f) => ({ ...f, categoriaId: e.target.value }))}>
            <option value="">-- Seleccionar --</option>
            {categorias.map((cat, i) => (
              <option key={i} value={i + 1}>{cat?.nombre ?? ''}</option>
            ))}
          </select>
        </div>
        <div className="field" style={{ gridColumn: 'span 3' }}>
          <label>Tecnología</label>
          <input value={form.tecnologia} onChange={(e) => setForm((f) => ({ ...f, tecnologia: e.target.value }))} />
        </div>
        <div className="field" style={{ gridColumn: 'span 4' }}>
          <label>Correo electrónico</label>
          <input type="email" value={form.correo} onChange={(e) => setForm((f) => ({ ...f, correo: e.target.value }))} />
        </div>
        <div className="field" style={{ gridColumn: 'span 4' }}>
          <label>Contraseña</label>
          <input type="password" value={form.password} onChange={(e) => setForm((f) => ({ ...f, password: e.target.value }))} placeholder="Dejar vacío para no cambiar" />
        </div>
        <div className="field" style={{ gridColumn: 'span 4' }}>
          <label>Rol</label>
          <select value={form.rol} onChange={(e) => setForm((f) => ({ ...f, rol: e.target.value }))}>
            <option value="">-- Seleccionar --</option>
            <option value="USUARIO">USUARIO</option>
            <option value="ADMINISTRADOR">ADMINISTRADOR</option>
          </select>
        </div>

        <div style={{ gridColumn: 'span 12', display: 'flex', gap: 10, flexWrap: 'wrap' }}>
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
            <th>Nombre</th>
            <th>Apellidos</th>
            <th>Correo</th>
            <th>Rol</th>
            <th>Categoría</th>
            <th>Tecnología</th>
          </tr>
        </thead>
        <tbody>
          {items.map((r, idx) => (
            <tr key={idx}>
              <td>{r.nombre}</td>
              <td>{r.apellidos}</td>
              <td>{r.correo ?? ''}</td>
              <td>{r.rol ?? ''}</td>
              <td>{r.categoriaNombre ?? ''}</td>
              <td>{r.tecnologia}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
