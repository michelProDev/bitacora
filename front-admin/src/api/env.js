export const ENV = {
  CRM_BASE_URL: import.meta.env.VITE_CRM_BASE_URL ?? 'http://localhost:8081',
  ACCOUNT_BASE_URL: import.meta.env.VITE_ACCOUNT_BASE_URL ?? 'http://localhost:8080',
  HR_BASE_URL: import.meta.env.VITE_HR_BASE_URL ?? 'http://localhost:8082',
  CATALOGO_BASE_URL: import.meta.env.VITE_CATALOGO_BASE_URL ?? 'http://localhost:8084',
};
