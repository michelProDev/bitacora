import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const correo = localStorage.getItem('correo');
    const nombre = localStorage.getItem('nombre');
    const rol = localStorage.getItem('rol');
    if (token && correo) {
      setUser({ token, correo, nombre, rol });
    }
    setLoading(false);
  }, []);

  function login(data) {
    localStorage.setItem('token', data.token);
    localStorage.setItem('correo', data.correo);
    localStorage.setItem('nombre', data.nombre);
    localStorage.setItem('rol', data.rol);
    setUser(data);
  }

  function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('correo');
    localStorage.removeItem('nombre');
    localStorage.removeItem('rol');
    setUser(null);
  }

  return (
    <AuthContext.Provider value={{ user, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
