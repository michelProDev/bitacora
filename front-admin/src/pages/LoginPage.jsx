import React, { useState } from 'react';
import { ENV } from '../api/env.js';
import { useAuth } from '../auth/AuthContext.jsx';

export default function LoginPage() {
  const { login } = useAuth();
  const [form, setForm] = useState({ correo: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  async function onSubmit(e) {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      const res = await fetch(`${ENV.HR_BASE_URL}/api/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      });
      const data = await res.json();
      if (!res.ok) {
        setError(data.error || 'Error de autenticación');
        return;
      }
      login(data);
    } catch (err) {
      setError('No se pudo conectar al servidor');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="login-wrapper">
      <form className="login-card" onSubmit={onSubmit}>
        <h1 className="login-title">Front Admin</h1>
        <p className="login-subtitle">Iniciar sesión</p>

        {error && <div className="alert error" style={{ marginBottom: 12 }}>{error}</div>}

        <div className="field" style={{ marginBottom: 12 }}>
          <label>Correo electrónico</label>
          <input
            type="email"
            value={form.correo}
            onChange={(e) => setForm((f) => ({ ...f, correo: e.target.value }))}
            required
            autoFocus
          />
        </div>

        <div className="field" style={{ marginBottom: 16 }}>
          <label>Contraseña</label>
          <input
            type="password"
            value={form.password}
            onChange={(e) => setForm((f) => ({ ...f, password: e.target.value }))}
            required
          />
        </div>

        <button className="primary" type="submit" disabled={loading} style={{ width: '100%' }}>
          {loading ? 'Ingresando...' : 'Ingresar'}
        </button>
      </form>
    </div>
  );
}
