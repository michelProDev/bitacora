# front-admin

Single frontend to manage your microservices:

- CRM micro (`crm-micro`) - Clientes
- Account micro (`account-micro`) - Proyectos
- HR micro (`hr`) - Recursos / Registros

## Setup

1. Configure endpoints:

Copy `.env.example` to `.env` and update if needed.

2. Install + run:

```bash
npm install
npm run dev
```

## Notes

- This UI calls each micro directly (different ports). If you get CORS errors, you must enable CORS on each Spring Boot app or place a gateway/reverse proxy in front.
- Expected REST paths:
  - CRM: `GET/POST/PUT/DELETE http://localhost:8081/api/clientes`
  - Account: `GET/POST/PUT/DELETE http://localhost:8080/api/proyectos`
  - HR: `GET/POST/PUT/DELETE http://localhost:8082/api/recursos`
  - HR: `GET/POST/PUT/DELETE http://localhost:8082/api/registros`
