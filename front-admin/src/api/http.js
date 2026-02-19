export async function httpJson(url, options = {}) {
  const token = localStorage.getItem('token');
  const res = await fetch(url, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...(options.headers ?? {}),
    },
  });

  const contentType = res.headers.get('content-type') ?? '';
  const isJson = contentType.includes('application/json');
  const body = isJson ? await res.json().catch(() => null) : await res.text().catch(() => null);

  if (!res.ok) {
    if (res.status === 401 || res.status === 403) {
      localStorage.removeItem('token');
      localStorage.removeItem('correo');
      localStorage.removeItem('nombre');
      localStorage.removeItem('rol');
      window.location.reload();
    }
    const err = new Error(`HTTP ${res.status}`);
    err.status = res.status;
    err.body = body;
    throw err;
  }

  return body;
}
