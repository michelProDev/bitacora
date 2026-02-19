import React, { useEffect, useMemo, useState } from 'react';
import { ENV } from '../api/env.js';
import { httpJson } from '../api/http.js';
import { useAuth } from '../auth/AuthContext.jsx';

export default function RegistrosPage({ isAdmin = false }) {
  const { user } = useAuth();
  const baseUrl = ENV.HR_BASE_URL;
  const catalogoUrl = ENV.CATALOGO_BASE_URL;
  const crmUrl = ENV.CRM_BASE_URL;
  const accountUrl = ENV.ACCOUNT_BASE_URL;
  const api = useMemo(() => ({
    list: () => httpJson(`${baseUrl}/api/registros`),
    get: (id) => httpJson(`${baseUrl}/api/registros/${id}`),
    create: (payload) => httpJson(`${baseUrl}/api/registros`, { method: 'POST', body: JSON.stringify(payload) }),
    update: (id, payload) => httpJson(`${baseUrl}/api/registros/${id}`, { method: 'PUT', body: JSON.stringify(payload) }),
    remove: (id) => httpJson(`${baseUrl}/api/registros/${id}`, { method: 'DELETE' }),
    listTiposRegistro: () => httpJson(`${catalogoUrl}/api/tipos-registro`),
    listEstadosTarea: () => httpJson(`${catalogoUrl}/api/estados-tarea`),
    listRecursos: () => httpJson(`${baseUrl}/api/recursos`),
    listClientes: () => httpJson(`${crmUrl}/api/clientes`),
    listProyectos: () => httpJson(`${accountUrl}/api/proyectos`),
  }), [baseUrl, catalogoUrl, crmUrl, accountUrl]);

  const emptyForm = {
    fecha: '',
    recursoNombre: !isAdmin && user?.nombre ? user.nombre : '',
    proyectoNombre: '',
    clienteNombre: '',
    horas: '',
    descripcion: '',
    tipoRegistroId: '',
    estadoTareaId: '',
  };

  const [items, setItems] = useState([]);
  const [tiposRegistro, setTiposRegistro] = useState([]);
  const [estadosTarea, setEstadosTarea] = useState([]);
  const [recursos, setRecursos] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [proyectos, setProyectos] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [form, setForm] = useState(emptyForm);
  const [selectedId, setSelectedId] = useState('');

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
    loadTiposRegistro();
    loadEstadosTarea();
    loadRecursos();
    loadClientes();
    loadProyectos();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  async function loadTiposRegistro() {
    try {
      const data = await api.listTiposRegistro();
      setTiposRegistro(Array.isArray(data) ? data : []);
    } catch (e) {
      console.error('Error loading tipos registro', e);
    }
  }

  async function loadEstadosTarea() {
    try {
      const data = await api.listEstadosTarea();
      setEstadosTarea(Array.isArray(data) ? data : []);
    } catch (e) {
      console.error('Error loading estados tarea', e);
    }
  }

  async function loadRecursos() {
    try {
      const data = await api.listRecursos();
      setRecursos(Array.isArray(data) ? data : []);
    } catch (e) {
      console.error('Error loading recursos', e);
    }
  }

  async function loadClientes() {
    try {
      const data = await api.listClientes();
      setClientes(Array.isArray(data) ? data : []);
    } catch (e) {
      console.error('Error loading clientes', e);
    }
  }

  async function loadProyectos() {
    try {
      const data = await api.listProyectos();
      setProyectos(Array.isArray(data) ? data : []);
    } catch (e) {
      console.error('Error loading proyectos', e);
    }
  }

  function toPayload() {
    return {
      fecha: form.fecha ? new Date(form.fecha).toISOString() : null,
      recursoNombre: form.recursoNombre || null,
      proyectoNombre: form.proyectoNombre || null,
      clienteNombre: form.clienteNombre || null,
      horas: form.horas ? Number(form.horas) : null,
      descripcion: form.descripcion,
      tipoRegistroId: form.tipoRegistroId ? Number(form.tipoRegistroId) : null,
      estadoTareaId: form.estadoTareaId ? Number(form.estadoTareaId) : null,
    };
  }

  async function onCreate() {
    setError('');
    try {
      await api.create(toPayload());
      setForm(emptyForm);
      await refresh();
    } catch (e) {
      // HR service returns 400 when cliente/proyecto refs are invalid
      setError(`${e.message}${e.body ? ` - ${JSON.stringify(e.body)}` : ''}`);
    }
  }

  async function onUpdate() {
    setError('');
    try {
      await api.update(selectedId, { ...toPayload() });
      await refresh();
    } catch (e) {
      setError(`${e.message}${e.body ? ` - ${JSON.stringify(e.body)}` : ''}`);
    }
  }

  async function onDelete() {
    setError('');
    try {
      await api.remove(selectedId);
      setSelectedId('');
      setForm(emptyForm);
      await refresh();
    } catch (e) {
      setError(`${e.message}${e.body ? ` - ${JSON.stringify(e.body)}` : ''}`);
    }
  }

  return (
    <div className="card">
      <div className="headerRow">
        <h1 className="h1">Registros</h1>
        <span className="muted">{baseUrl}</span>
      </div>

      {error ? (
        <div className="alert error" style={{ marginBottom: 12 }}>
          {error}
          <div className="muted" style={{ marginTop: 6 }}>
            If you get <span className="kbd">HTTP 400</span>, it usually means <span className="kbd">cliente</span> or{' '}
            <span className="kbd">proyecto</span> does not exist in the other microservices.
          </div>
        </div>
      ) : null}

      <div className="row" style={{ marginBottom: 12 }}>
        <div className="field" style={{ gridColumn: 'span 3' }}>
          <label>Fecha</label>
          <input type="datetime-local" value={form.fecha} onChange={(e) => setForm((f) => ({ ...f, fecha: e.target.value }))} />
        </div>
        <div className="field" style={{ gridColumn: 'span 3' }}>
          <label>Recurso</label>
          {isAdmin ? (
            <select value={form.recursoNombre} onChange={(e) => setForm((f) => ({ ...f, recursoNombre: e.target.value }))}>
              <option value="">-- Seleccionar --</option>
              {recursos.map((r, i) => (
                <option key={i} value={r?.nombre ?? ''}>{r?.nombre ?? ''}</option>
              ))}
            </select>
          ) : (
            <input value={form.recursoNombre} disabled />
          )}
        </div>
        <div className="field" style={{ gridColumn: 'span 3' }}>
          <label>Proyecto</label>
          <select value={form.proyectoNombre} onChange={(e) => setForm((f) => ({ ...f, proyectoNombre: e.target.value }))}>
            <option value="">-- Seleccionar --</option>
            {proyectos.map((p, i) => (
              <option key={i} value={p?.nombreProyecto ?? ''}>{p?.nombreProyecto ?? ''}</option>
            ))}
          </select>
        </div>
        <div className="field" style={{ gridColumn: 'span 3' }}>
          <label>Cliente</label>
          <select value={form.clienteNombre} onChange={(e) => setForm((f) => ({ ...f, clienteNombre: e.target.value }))}>
            <option value="">-- Seleccionar --</option>
            {clientes.map((c, i) => (
              <option key={i} value={c?.nombre ?? ''}>{c?.nombre ?? ''}</option>
            ))}
          </select>
        </div>

        <div className="field" style={{ gridColumn: 'span 3' }}>
          <label>Horas</label>
          <input value={form.horas} onChange={(e) => setForm((f) => ({ ...f, horas: e.target.value }))} />
        </div>
        <div className="field" style={{ gridColumn: 'span 3' }}>
          <label>Tipo</label>
          <select value={form.tipoRegistroId} onChange={(e) => setForm((f) => ({ ...f, tipoRegistroId: e.target.value }))}>
            <option value="">-- Seleccionar --</option>
            {tiposRegistro.map((t, i) => (
              <option key={i} value={i + 1}>{t?.nombre ?? ''}</option>
            ))}
          </select>
        </div>
        <div className="field" style={{ gridColumn: 'span 3' }}>
          <label>Estado</label>
          <select value={form.estadoTareaId} onChange={(e) => setForm((f) => ({ ...f, estadoTareaId: e.target.value }))}>
            <option value="">-- Seleccionar --</option>
            {estadosTarea.map((et, i) => (
              <option key={i} value={i + 1}>{et?.nombre ?? ''}</option>
            ))}
          </select>
        </div>
        <div className="field" style={{ gridColumn: 'span 12' }}>
          <label>Descripción</label>
          <textarea value={form.descripcion} onChange={(e) => setForm((f) => ({ ...f, descripcion: e.target.value }))} />
        </div>

        <div style={{ gridColumn: 'span 12', display: 'flex', gap: 10, flexWrap: 'wrap' }}>
          <button className="primary" onClick={onCreate}>Create</button>
          <button onClick={refresh} disabled={loading}>{loading ? 'Loading...' : 'Refresh'}</button>
        </div>
      </div>

      {isAdmin && (
      <div className="row" style={{ marginBottom: 12 }}>
        <div className="field" style={{ gridColumn: 'span 6' }}>
          <label>ID (para actualizar/eliminar)</label>
          <input value={selectedId} onChange={(e) => setSelectedId(e.target.value)} placeholder="e.g. 1" />
        </div>
        <div style={{ gridColumn: 'span 6', display: 'flex', alignItems: 'flex-end', gap: 10 }}>
          <button onClick={onUpdate} disabled={!selectedId}>Update</button>
          <button className="danger" onClick={onDelete} disabled={!selectedId}>Delete</button>
        </div>
      </div>
      )}

      <table className="table">
        <thead>
          <tr>
            <th>Fecha</th>
            <th>Recurso</th>
            <th>Proyecto</th>
            <th>Cliente</th>
            <th>Horas</th>
            <th>Tipo</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          {items.map((r, idx) => (
            <tr key={idx}>
              <td>{r.fecha ? String(r.fecha) : ''}</td>
              <td>{r.recursoNombre ?? ''}</td>
              <td>{r.proyectoNombre ?? ''}</td>
              <td>{r.clienteNombre ?? ''}</td>
              <td>{r.horas}</td>
              <td>{r.tipoRegistroNombre ?? ''}</td>
              <td>{r.estadoTareaNombre ?? ''}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
