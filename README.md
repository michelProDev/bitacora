# 📘 Bitácora de Tareas Diarias para Desarrolladores

## 🧭 Objetivo del Proyecto

Este proyecto tiene como finalidad proporcionar una plataforma organizada y eficiente para el registro diario de las tareas realizadas por desarrolladores. Funciona como una bitácora digital que permite documentar avances, facilitar el seguimiento de actividades y mejorar la trazabilidad del trabajo dentro de equipos de desarrollo.

---

## 🛠️ Tecnologías Utilizadas

El sistema ha sido desarrollado utilizando una arquitectura moderna y escalable basada en microservicios, con el siguiente stack tecnológico:

### 🔙 Back-End

- **Lenguaje:** Java  
- **Framework:** Spring Boot  
- **Funcionalidad:** Exposición de APIs RESTful para la gestión de usuarios, autenticación, creación y consulta de tareas diarias.

### 🎨 Front-End

- **Librería:** React  
- **Funcionalidad:** Interfaz de usuario intuitiva y responsiva para el registro y visualización de tareas diarias.

### 🗄️ Base de Datos

- **Motor:** MySQL  
- **Funcionalidad:** Almacenamiento persistente y estructurado de toda la información relativa a usuarios, tareas y registros.

### 🐳 Despliegue

- **Contenedores:** Docker  
- **Funcionalidad:** Solución completamente dockerizada que permite una configuración y despliegue sencillo y reproducible del entorno de desarrollo y producción mediante `Docker Compose`.

---

## 📁 Estructura del Proyecto

```plaintext
bitacora-tareas/
├── backend/
│   └── src/
│       └── main/
│           └── java/
│               └── com/
│                   └── bitacora/
│                       ├── controller/
│                       ├── service/
│                       ├── model/
│                       └── repository/
│           └── resources/
│               ├── application.properties
│               └── ...
│   └── Dockerfile
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   └── App.jsx
│   └── Dockerfile
├── db/
│   └── init.sql
├── docker-compose.yml
└── README.md
```

---

## 🚀 Cómo Ejecutar el Proyecto

1. **Clonar el repositorio:**

```bash
git clone https://github.com/michelProDev/bitacora.git
cd bitacora
```

2. **Levantar el entorno con Docker:**

```bash
docker-compose up --build
```

3. **Acceder a la aplicación:**

- Frontend: [http://localhost:3000](http://localhost:3000)  
- Backend (API): [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

---

## 📄 Licencia

Este proyecto está licenciado bajo los términos de la [MIT License](LICENSE).
