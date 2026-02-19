# Bitácora — Microservices Architecture with Monitoring

A Spring Boot microservices project with Eureka service discovery, JWT authentication, and a real-time monitoring dashboard for observability metrics.

## 🏗️ Architecture Overview

```
┌─────────────────┐    ┌─────────────────┐    
│   front-admin   │    │ monitoring-front│    
│   (Port 5173)   │    │   (Port 3002)   │    
└─────────┬───────┘    └──────────┬──────┘    
          │                       │           
          └───────────────────────┼
                                  │
                    ┌─────────────▼─────────────┐
                    │      API Gateway          │
                    │   (Spring Cloud Gateway)  │
                    └─────────────┬─────────────┘
                                  │
                    ┌─────────────▼─────────────┐
                    │     Eureka Server         │
                    │   discovery:8761          │
                    └─────────────┬─────────────┘
                                  │
          ┌───────────────────────┼──────────────────────┐
          │                       │                      │
┌─────────▼───────┐    ┌──────────▼──────┐    ┌──────────▼──────┐
│   HR Service    │    │  Account Service│    │   CRM Service   │
│     hr:8082     │    │   account:8080  │    │   crm:8081      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                  │
                    ┌─────────────▼───────┐
                    │ Catalog Service     │
                    │ catalogos:8084      │
                    └─────────────────────┘
```

## 📋 Services

| Service | Port | Description | Database |
|---------|------|-------------|----------|
| **discovery** | 8761 | Eureka Service Registry | None |
| **hr** | 8082 | Human Resources & Time Tracking | MySQL |
| **account** | 8080 | Account Management | MySQL |
| **cliente-micro** | 8081 | Customer Relationship Management (CRM) | MySQL |
| **catalogo-micro** | 8084 | Product/Service Catalog | MySQL |
| **front-admin** | 5173 | Admin Dashboard (React) | None |
| **monitoring-front** | 3002 | Observability Dashboard (React) | None |

## 🚀 Quick Start

### Prerequisites

- **Docker Desktop** (recommended) OR:
  - **Java 17+**
  - **Maven 3.8+**
  - **Node.js 18+**
  - **MySQL 8.0+**
  - **Git**

---

## 🐳 Docker Compose (Recommended)

Start the entire stack with a single command:

```bash
# Clone and navigate to project
git clone <repository-url>
cd bitacora

# Start all services (builds images if needed)
docker compose up --build

# Stop all services
docker compose down

# Remove volumes (delete database)
docker compose down -v
```

### Services started

| Service | Port | Access URL |
|---------|------|------------|
| **MySQL** | 3306 | `mysql://localhost:3306/bitacoraData` |
| **Eureka** | 8761 | http://localhost:8761 |
| **HR Service** | 8082 | http://localhost:8082/swagger-ui |
| **Account Service** | 8080 | http://localhost:8080/swagger-ui |
| **CRM Service** | 8081 | http://localhost:8081/swagger-ui |
| **Catalog Service** | 8084 | http://localhost:8084/swagger-ui |
| **Admin Dashboard** | 5173 | http://localhost:5173 |
| **Monitoring Dashboard** | 3002 | http://localhost:3002 |

### Default Users

- **Admin**: `admin@btech.com` / `admin123`
- **Usuario**: `usuario@btech.com` / `user123`

---

## 🔧 Manual Setup (Alternative)

### 1. Database Setup

```sql
CREATE DATABASE bitacoraData CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Start Microservices

```bash
# Start Eureka Server
cd discovery
mvn spring-boot:run

# Start other services (in separate terminals)
cd hr
mvn spring-boot:run

cd account
mvn spring-boot:run

cd cliente-micro
mvn spring-boot:run

cd catalogo-micro
mvn spring-boot:run
```

### 3. Start Frontend Applications

```bash
# Admin Dashboard
cd front-admin
npm install
npm run dev

# Monitoring Dashboard
cd monitoring-front
npm install
npm run dev
```

### 4. Access Applications

- **Admin Dashboard**: http://localhost:5173
- **Monitoring Dashboard**: http://localhost:3002
- **Eureka Console**: http://localhost:8761
- **API Documentation**: http://localhost:8082/swagger-ui (HR service example)

## 🔧 Configuration

### Configuration Templates

Each Java microservice includes an `application.properties.example` file. Copy it to
`application.properties` and customize secrets/DB credentials as needed:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

> Note: `application.properties` is gitignored to avoid committing secrets.

### Environment Variables

Each microservice uses the following key configurations:

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/bitacoraData
spring.datasource.username=root

# Eureka
eureka.client.service-url.defaultZone=http://localhost:8761/eureka

# Actuator & Monitoring
management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.endpoints.web.cors.allowed-origins=http://localhost:5173,http://localhost:3002

# Percentile Metrics
management.metrics.distribution.percentiles.http.server.requests=0.5,0.9,0.95,0.99
management.metrics.distribution.percentiles-histogram.http.server.requests=true
```

### CORS Configuration

All services accept requests from:
- `http://localhost:5173` (Admin Dashboard)
- `http://localhost:3002` (Monitoring Dashboard)

## 📊 Monitoring & Observability

### Available Metrics

The monitoring dashboard displays:

| Metric | Source | Description |
|--------|--------|-------------|
| **Service Health** | `/actuator/health` | UP/DOWN status, DB connectivity |
| **JVM Memory** | `/actuator/prometheus` | Used/Max heap memory |
| **CPU Usage** | `/actuator/prometheus` | Process CPU utilization |
| **HTTP Requests** | `/actuator/prometheus` | Total request count |
| **P50/P90/P95/P99** | `/actuator/prometheus` | HTTP request latency percentiles |
| **Disk Space** | `/actuator/health` | Free/total disk space |
| **Thread Count** | `/actuator/prometheus` | Live JVM threads |
| **Uptime** | `/actuator/prometheus` | Service running time |

### Monitoring Features

- **Real-time polling** every 5 seconds
- **Historical charts** for JVM memory, CPU, and P90 latency
- **Service status badges** with color coding
- **Percentile latency tracking** with P90 highlighted
- **Responsive design** for mobile/desktop

## 🔐 Authentication

- **JWT-based authentication** in HR service
- **Role-based access control**: ADMIN, USUARIO
- **Default credentials** (check individual service properties)
- **Token expiration**: 24 hours

## 📝 API Documentation

Each service exposes Swagger/OpenAPI documentation:

- HR Service: http://localhost:8082/swagger-ui
- Account Service: http://localhost:8080/swagger-ui
- CRM Service: http://localhost:8081/swagger-ui
- Catalog Service: http://localhost:8084/swagger-ui

## 🛠️ Development

### Project Structure

```
bitacora/
├── discovery/                 # Eureka Server
├── hr/                       # HR Microservice
├── account/                  # Account Microservice
├── cliente-micro/            # CRM Microservice
├── catalogo-micro/           # Catalog Microservice
├── front-admin/              # Admin Dashboard (React)
├── monitoring-front/         # Monitoring Dashboard (React)
└── README.md                 # This file
```

### Adding New Services

1. Create new Spring Boot project with Eureka Client
2. Add dependencies:
   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
   </dependency>
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-actuator</artifactId>
   </dependency>
   <dependency>
       <groupId>io.micrometer</groupId>
       <artifactId>micrometer-registry-prometheus</artifactId>
   </dependency>
   ```
3. Configure Eureka client in `application.properties`
4. Add CORS configuration for `/actuator/**` endpoints
5. Add percentile metrics properties
6. Update monitoring dashboard `SERVICES` array

### Database Migrations

- **Hibernate DDL**: `spring.jpa.hibernate.ddl-auto=update`
- **Schema changes** are automatically applied on startup
- **Production**: Consider Flyway/Liquibase for proper migrations

## 🔍 Troubleshooting

### Common Issues

**Services not registering with Eureka**
- Check Eureka server is running on port 8761
- Verify `eureka.client.service-url.defaultZone` configuration
- Check network connectivity

**Monitoring dashboard shows no metrics**
- Ensure all services have `micrometer-registry-prometheus` dependency
- Verify `/actuator/prometheus` endpoint is accessible
- Check CORS configuration includes `http://localhost:3002`
- Services need HTTP traffic to generate percentile metrics

**Database connection errors**
- Verify MySQL is running on localhost:3306
- Check database `bitacoraData` exists
- Verify username/password credentials

**Frontend build errors**
- Run `npm install` in each frontend directory
- Clear node_modules and reinstall if needed
- Check Node.js version compatibility

### Health Checks

Monitor service health:
```bash
curl http://localhost:8761/actuator/health
curl http://localhost:8082/actuator/health
curl http://localhost:3002
```

## 📦 Deployment

### Docker (Future)

```bash
# Build images
docker build -t bitacora/discovery ./discovery
docker build -t bitacora/hr ./hr
# ... other services

# Run with Docker Compose
docker-compose up -d
```

### Production Considerations

- **Environment variables** for secrets
- **External database** (RDS, Cloud SQL)
- **Load balancer** for API Gateway
- **Centralized logging** (ELK stack)
- **Metrics aggregation** (Prometheus + Grafana)
- **Circuit breakers** (Hystrix/Resilience4j)

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🔗 Links

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Cloud Netflix](https://spring.io/projects/spring-cloud-netflix)
- [Micrometer Documentation](https://micrometer.io/)
- [React Documentation](https://reactjs.org/)
- [Eureka Documentation](https://cloud.spring.io/spring-cloud-netflix/multi/multi__service_discovery_eureka_clients.html)

---

**Built with ❤️ using Spring Boot, React, and modern observability tools**
