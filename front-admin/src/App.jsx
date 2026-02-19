import React, { useState } from 'react';
import { NavLink, Route, Routes, useLocation } from 'react-router-dom';
import { AuthProvider, useAuth } from './auth/AuthContext.jsx';
import LoginPage from './pages/LoginPage.jsx';
import Home from './pages/Home.jsx';
import ClientesPage from './pages/ClientesPage.jsx';
import ProyectosPage from './pages/ProyectosPage.jsx';
import RecursosPage from './pages/RecursosPage.jsx';
import RegistrosPage from './pages/RegistrosPage.jsx';
import PaisesPage from './pages/PaisesPage.jsx';
import CategoriasPage from './pages/CategoriasPage.jsx';
import EstadosTareaPage from './pages/EstadosTareaPage.jsx';
import TiposRegistroPage from './pages/TiposRegistroPage.jsx';

const menuGroups = [
  {
    label: 'Catálogos',
    prefix: 'catalogos',
    adminOnly: true,
    items: [
      { to: '/paises', label: 'Paises' },
      { to: '/categorias', label: 'Categorias' },
      { to: '/estados-tarea', label: 'Estados Tarea' },
      { to: '/tipos-registro', label: 'Tipos Registro' },
    ],
  },
  {
    label: 'CRM',
    prefix: 'crm',
    adminOnly: true,
    items: [
      { to: '/clientes', label: 'Clientes' },
    ],
  },
  {
    label: 'Account',
    prefix: 'account',
    adminOnly: true,
    items: [
      { to: '/proyectos', label: 'Proyectos' },
    ],
  },
  {
    label: 'HR',
    prefix: 'hr',
    adminOnly: false,
    items: [
      { to: '/recursos', label: 'Recursos', adminOnly: true },
      { to: '/registros', label: 'Bitácora' },
    ],
  },
];

function MenuGroup({ label, items, prefix }) {
  const location = useLocation();
  const isChildActive = items.some((item) => location.pathname === item.to);
  const [open, setOpen] = useState(isChildActive);

  return (
    <div className="menu-group">
      <button
        className={`menu-group-toggle${open ? ' open' : ''}${isChildActive ? ' child-active' : ''}`}
        onClick={() => setOpen((o) => !o)}
      >
        <span>{label}</span>
        <span className={`chevron${open ? ' rotated' : ''}`}>&#9662;</span>
      </button>
      {open && (
        <div className="menu-group-items">
          {items.map((item) => (
            <NavLink
              key={item.to}
              to={item.to}
              className={({ isActive }) => `menu-item${isActive ? ' active' : ''}`}
            >
              {item.label}
            </NavLink>
          ))}
        </div>
      )}
    </div>
  );
}

function AppContent() {
  const { user, logout, loading } = useAuth();

  if (loading) return null;
  if (!user) return <LoginPage />;

  const isAdmin = user.rol === 'ADMINISTRADOR';

  return (
    <div className="app">
      <aside className="sidebar">
        <div className="brand">Front Admin</div>
        <div className="user-info">
          <span className="user-name">{user.nombre}</span>
          <span className="user-role">{user.rol}</span>
        </div>
        <nav className="nav">
          <NavLink to="/" end className={({ isActive }) => `menu-item${isActive ? ' active' : ''}`}>
            Home
          </NavLink>
          {menuGroups
            .filter((g) => isAdmin || !g.adminOnly)
            .map((g) => {
              const visibleItems = isAdmin ? g.items : g.items.filter((i) => !i.adminOnly);
              if (visibleItems.length === 0) return null;
              return <MenuGroup key={g.prefix} label={g.label} items={visibleItems} prefix={g.prefix} />;
            })}
        </nav>
        <button className="logout-btn" onClick={logout}>Cerrar sesión</button>
      </aside>

      <main className="main">
        <Routes>
          <Route path="/" element={<Home />} />
          {isAdmin && <Route path="/clientes" element={<ClientesPage />} />}
          {isAdmin && <Route path="/proyectos" element={<ProyectosPage />} />}
          {isAdmin && <Route path="/recursos" element={<RecursosPage />} />}
          <Route path="/registros" element={<RegistrosPage isAdmin={isAdmin} />} />
          {isAdmin && <Route path="/paises" element={<PaisesPage />} />}
          {isAdmin && <Route path="/categorias" element={<CategoriasPage />} />}
          {isAdmin && <Route path="/estados-tarea" element={<EstadosTareaPage />} />}
          {isAdmin && <Route path="/tipos-registro" element={<TiposRegistroPage />} />}
        </Routes>
      </main>
    </div>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  );
}
