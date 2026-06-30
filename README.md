
  # 🚀 SISTEMA DE MICROSERVICIOS MULTIMÓDULO - ENTREGA FINAL


## 📦 COMPONENTES DE DISTRIBUCIÓN Y DEFENSA TÉCNICA

Utilice los siguientes enlaces externos para descargar las versiones listas para producción y visualizar la defensa del proyecto:



| Componente | Descripción | Enlace de Descarga (Nube externa) |

| :--- | :--- | :--- |

| **📦 Versión Sin Docker** <br>*(Arranque Nativo)* | Archivo `.zip` que contiene la carpeta `apps/` con los `.jar` compilados y el script `arrancar-nativo.bat` ordenado por fases. | [Descargar ZIP Nativo aquí](https://drive.google.com/file/d/1azkHPlvBv49VBWWkD2a84scpTL6op_9o/view?usp=sharing) |

| **🐳 Versión Con Docker** <br>*(Avance Examen Transversal)* | Archivo `.zip` que contiene la carpeta `apps/` con los `.jar`, el archivo `docker-compose.yml` y el script automatizado `arrancar-sistema.bat`. | [Descargar ZIP Docker aquí](https://drive.google.com/file/d/1pQeJgMBx8Pb-p990V4KgjTFmTtvPplcA/view?usp=sharing) |

| **🎥 Video de Defensa Técnica** <br>*(Evaluación Individual)* | Enlace directo al video explicativo donde se evidencia el funcionamiento, testing y el aporte técnico individual. **Duración ideal: 15 minutos (Máximo permitido: 18 minutos).** | [Ver Video Explicativo aquí](https://drive.google.com/drive/folders/1TwkMSGTVrnF7GZKkhrhVfHe2NaTwxP6j?usp=sharing) |


# 🚗 Sistema de Taller Mecánico y Venta de Repuestos

Backend desarrollado con **Java 17**, **Spring Boot**, **Spring Cloud**, **Maven Multi-Módulo** y **Docker**, basado en una arquitectura de microservicios para administrar las operaciones de un taller mecánico.

## 👥 Integrantes

- Johan Codoceo
- José Manuel Venegas Espinoza

---

# 📖 Contexto del Proyecto

El sistema permite administrar:

- Usuarios
- Autenticación JWT
- Clientes
- Vehículos
- Mecánicos
- Órdenes de trabajo
- Inventario
- Ventas
- Pagos
- Eventos de seguridad

---

# 🛠 Tecnologías

- Java 17
- Spring Boot
- Spring Cloud
- Eureka Server
- Spring Cloud Gateway
- Spring Security
- JWT
- Spring Data JPA
- MySQL
- Maven
- Docker
- Swagger/OpenAPI
- JUnit 5
- Mockito
- SLF4J / Logback

---

# 🏗 Arquitectura

```text
Cliente / Postman
        │
        ▼
API Gateway (8080)
        │
        ▼
Eureka Server (8761)
        │
 ├── auth-service
 ├── user-service
 ├── cliente-service
 ├── vehiculo-service
 ├── mecanico-service
 ├── orden-trabajo-service
 ├── inventario-service
 ├── pago-service
 ├── venta-service
 └── security-service
```

---

# 📦 Microservicios

| Servicio | Puerto | Función |
|---|---:|---|
| Eureka Server | 8761 | Registro de servicios |
| API Gateway | 8080 | Punto de acceso |
| Auth Service | 8081 | Autenticación |
| User Service | 8082 | Usuarios |
| Cliente Service | 8083 | Clientes |
| Vehículo Service | 8084 | Vehículos |
| Orden Trabajo Service | 8085 | Órdenes |
| Inventario Service | 8086 | Inventario |
| Pago Service | 8087 | Pagos |
| Mecánico Service | 8088 | Mecánicos |
| Venta Service | 8089 | Ventas |
| Security Service | 8090 | Auditoría |

---

# 🌐 Rutas principales del Gateway

- `/auth/**`
- `/users/**`
- `/clientes/**`
- `/vehiculos/**`
- `/mecanicos/**`
- `/inventario/**`
- `/ordenes/**`
- `/pagos/**`
- `/ventas/**`
- `/security-events/**`

---

# 📚 Documentación Swagger

Cada microservicio dispone de Swagger:

- http://localhost:8081/swagger-ui/index.html
- http://localhost:8082/swagger-ui/index.html
- http://localhost:8083/swagger-ui/index.html
- http://localhost:8084/swagger-ui/index.html
- http://localhost:8085/swagger-ui/index.html
- http://localhost:8086/swagger-ui/index.html
- http://localhost:8087/swagger-ui/index.html
- http://localhost:8088/swagger-ui/index.html
- http://localhost:8089/swagger-ui/index.html
- http://localhost:8090/swagger-ui/index.html

---

# ▶ Ejecución Local

## Compilar

```bash
mvn clean install
```

## Versión Nativa

Ejecutar:

```text
arrancar-nativo.bat
```

Orden de inicio:

1. Eureka Server
2. Microservicios
3. API Gateway

Detener:

```text
detener-nativo.bat
```

---

# 🐳 Ejecución Docker

Ejecutar:

```bash
docker compose up -d
```

Detener:

```bash
docker compose down
```

---

# 🧪 Pruebas Unitarias

El proyecto incorpora:

- JUnit 5
- Mockito

Las pruebas se ejecutan mediante:

```bash
mvn clean install
```

o

```bash
mvn test
```

La cobertura objetivo del proyecto es igual o superior al **80%**.

---

# 📋 Logs

Se implementaron logs estructurados para:

- Solicitudes HTTP
- Operaciones de negocio
- JWT
- Excepciones
- Auditoría

---
---

# 📝 Subtítulos del Video

Como parte del material complementario de la entrega, se incluye el archivo:

```text
subtitulos-video.txt https://drive.google.com/file/d/1mWj6oMr8G6PAQ-UwVEXppBvcQ9Ninvgf/view?usp=sharing
```

Este archivo contiene la transcripción del video de defensa técnica y se incorpora para cumplir con los requisitos de accesibilidad establecidos en la Evaluación Parcial 3.

---

# ✅ Checklist

- ✅ Contexto del proyecto
- ✅ Integrantes
- ✅ Microservicios implementados
- ✅ Gateway documentado
- ✅ Swagger/OpenAPI
- ✅ Ejecución local
- ✅ Ejecución remota
- ✅ JUnit 5
- ✅ Mockito
- ✅ Cobertura ≥80%
- ✅ Docker
- ✅ Video
- ✅ Subtítulos

---

# 📄 Observaciones

Este README fue estructurado para cumplir con los requisitos de la Evaluación Parcial 3, facilitando la instalación, ejecución y evaluación autónoma del proyecto.

