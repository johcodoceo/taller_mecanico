
  # 🚀 SISTEMA DE MICROSERVICIOS MULTIMÓDULO - ENTREGA FINAL


## 📦 COMPONENTES DE DISTRIBUCIÓN Y DEFENSA TÉCNICA

Utilice los siguientes enlaces externos para descargar las versiones listas para producción y visualizar la defensa del proyecto:



| Componente | Descripción | Enlace de Descarga (Nube externa) |

| :--- | :--- | :--- |

| **📦 Versión Sin Docker** <br>*(Arranque Nativo)* | Archivo `.zip` que contiene la carpeta `apps/` con los `.jar` compilados y el script `arrancar-nativo.bat` ordenado por fases. | [Descargar ZIP Nativo aquí](https://drive.google.com/file/d/1azkHPlvBv49VBWWkD2a84scpTL6op_9o/view?usp=sharing) |

| **🐳 Versión Con Docker** <br>*(Avance Examen Transversal)* | Archivo `.zip` que contiene la carpeta `apps/` con los `.jar`, el archivo `docker-compose.yml` y el script automatizado `arrancar-sistema.bat`. | [Descargar ZIP Docker aquí](https://drive.google.com/file/d/1pQeJgMBx8Pb-p990V4KgjTFmTtvPplcA/view?usp=sharing) |

| **🎥 Video de Defensa Técnica** <br>*(Evaluación Individual)* | Enlace directo al video explicativo donde se evidencia el funcionamiento, testing y el aporte técnico individual. **Duración ideal: 15 minutos (Máximo permitido: 18 minutos).** | [Ver Video Explicativo aquí](https://drive.google.com/drive/folders/1TwkMSGTVrnF7GZKkhrhVfHe2NaTwxP6j?usp=sharing) |


---

Sistema de Taller Mecánico y Venta de Repuestos
Integrantes
Johan Codoceo
José Venegas


1. Descripción del Contexto del Proyecto

El presente proyecto corresponde al desarrollo del backend de un sistema de gestión para un taller mecánico y venta de repuestos, implementado mediante una arquitectura basada en microservicios.

La solución permite administrar de forma independiente los distintos procesos del negocio, desacoplando cada responsabilidad en un microservicio especializado y facilitando la escalabilidad, mantenibilidad y despliegue del sistema.

Entre las funcionalidades principales se encuentran:

Gestión de usuarios.
Registro y autenticación mediante JWT.
Administración de clientes.
Gestión de vehículos.
Administración de mecánicos.
Gestión de órdenes de trabajo.
Administración de inventario.
Registro de ventas.
Gestión de pagos.
Registro de eventos de seguridad y auditoría.
2. Objetivo del Sistema

Desarrollar una solución distribuida basada en microservicios utilizando Spring Boot, Spring Cloud y Maven Multi-Módulo que permita administrar de forma integral los procesos de un taller mecánico mediante servicios independientes, documentados y desacoplados.

3. Tecnologías Utilizadas
Tecnología	Uso
Java 17	Lenguaje principal
Spring Boot 3	Desarrollo de microservicios
Spring Cloud	Comunicación distribuida
Spring Cloud Gateway	API Gateway
Eureka Server	Descubrimiento de servicios
Spring Security	Seguridad
JWT	Autenticación
Spring Data JPA	Persistencia
MySQL	Base de datos
Maven	Gestión del proyecto
Docker	Contenedores
Swagger / OpenAPI	Documentación REST
JUnit 5	Pruebas unitarias
Mockito	Simulación de dependencias
SLF4J / Logback	Logs estructurados
4. Arquitectura General
Cliente / Frontend / Postman
            │
            ▼
      API Gateway
      Puerto 8080
            │
            ▼
     Eureka Server
      Puerto 8761
            │
 ┌──────────┼──────────┐
 │          │          │
 ▼          ▼          ▼
Microservicios independientes

Cada microservicio posee:

Responsabilidad única.
Base de datos independiente.
Registro automático en Eureka.
Documentación Swagger.
Pruebas unitarias.
Logs estructurados.
5. Microservicios Implementados
Servicio	Puerto	Función
Eureka Server	8761	Registro de microservicios
API Gateway	8080	Punto único de acceso
Auth Service	8081	Registro, Login y JWT
User Service	8082	Gestión de usuarios
Cliente Service	8083	Gestión de clientes
Vehículo Service	8084	Gestión de vehículos
Orden Trabajo Service	8085	Gestión de órdenes
Inventario Service	8086	Gestión de inventario
Pago Service	8087	Gestión de pagos
Mecánico Service	8088	Gestión de mecánicos
Venta Service	8089	Gestión de ventas
Security Service	8090	Eventos de seguridad
6. Rutas Principales del API Gateway

Todas las solicitudes externas ingresan mediante el Gateway.

Ruta	Microservicio
/auth/**	Auth Service
/users/**	User Service
/clientes/**	Cliente Service
/vehiculos/**	Vehículo Service
/mecanicos/**	Mecánico Service
/inventario/**	Inventario Service
/ordenes/**	Orden Trabajo Service
/pagos/**	Pago Service
/ventas/**	Venta Service
/security-events/**	Security Service

Gateway:

http://localhost:8080
7. Arquitectura por Capas

Todos los microservicios utilizan la siguiente arquitectura:

Controller
     │
     ▼
Service
     │
     ▼
Repository
     │
     ▼
Entity

Capas implementadas:

Controller
Service
Repository
Entity
DTO
Exception
Config
Security

Esta estructura favorece:

Escalabilidad.
Bajo acoplamiento.
Reutilización.
Facilidad de pruebas.
8. Documentación Swagger/OpenAPI

Cada microservicio dispone de documentación automática mediante Swagger/OpenAPI.

Servicio	URL
Auth Service	http://localhost:8081/swagger-ui/index.html
User Service	http://localhost:8082/swagger-ui/index.html
Cliente Service	http://localhost:8083/swagger-ui/index.html
Vehículo Service	http://localhost:8084/swagger-ui/index.html
Orden Trabajo Service	http://localhost:8085/swagger-ui/index.html
Inventario Service	http://localhost:8086/swagger-ui/index.html
Pago Service	http://localhost:8087/swagger-ui/index.html
Mecánico Service	http://localhost:8088/swagger-ui/index.html
Venta Service	http://localhost:8089/swagger-ui/index.html
Security Service	http://localhost:8090/swagger-ui/index.html
9. Ejecución Local
Requisitos
Java JDK 17
Maven 3.9+
MySQL (XAMPP)
Docker Desktop (solo versión Docker)
Compilar el proyecto
mvn clean install
Ejecución automática

Versión Nativa

arrancar-nativo.bat

Orden de inicio:

Eureka Server
Microservicios
API Gateway

Para detener:

detener-nativo.bat
10. Ejecución Remota

La entrega incorpora dos modalidades:

Versión Nativa

Incluye:

apps/
JAR compilados
arrancar-nativo.bat
detener-nativo.bat
Versión Docker

Incluye:

apps/
docker-compose.yml
arrancar-sistema.bat
detener-sistema.bat
11. Pruebas Unitarias

El proyecto incorpora pruebas unitarias desarrolladas mediante:

JUnit 5
Mockito

Las dependencias de prueba fueron centralizadas en el POM padre para ser heredadas por todos los microservicios.

Servicios cubiertos:

Auth Service
User Service
Cliente Service
Vehículo Service
Mecánico Service
Inventario Service
Orden Trabajo Service
Pago Service
Venta Service
Security Service

Las pruebas pueden ejecutarse mediante:

mvn test

o

mvn clean install
Cobertura

El proyecto fue desarrollado considerando una cobertura de pruebas igual o superior al 80% sobre la lógica implementada en los servicios.

En caso de utilizar JaCoCo, el reporte puede obtenerse mediante:

target/site/jacoco/index.html
12. Logs Estructurados

El sistema incorpora logs estructurados para registrar:

Solicitudes HTTP.
Operaciones de negocio.
Eventos JWT.
Validaciones.
Excepciones.
Errores.

Ejemplo:

event=http_request_complete
method=POST
path=/clientes
status=201
durationMs=42
13. Seguridad

La autenticación utiliza JWT.

Flujo:

Registro.
Login.
Obtención del token.
Consumo de endpoints protegidos.

Header:

Authorization: Bearer TOKEN
14. Video de Defensa

El video incluye:

Arquitectura.
Microservicios.
Swagger.
Logs.
Docker.
Testing.
Funcionamiento completo.

Duración:

Ideal: 15 minutos.
Máximo: 18 minutos.
15. Requisitos del Sistema
Herramienta	Versión
Java	17
Maven	3.9+
MySQL	8
Docker	Última
Postman	Opcional
16. Checklist de Cumplimiento
Requisito	Estado
Descripción del dominio	✅
Integrantes	✅
Arquitectura de microservicios	✅
POM Padre	✅
Eureka Server	✅
API Gateway	✅
Listado de microservicios	✅
Rutas del Gateway	✅
Swagger/OpenAPI	✅
Ejecución local	✅
Ejecución remota	✅
Docker	✅
JUnit 5	✅
Mockito	✅
Cobertura de pruebas (≥80%)	✅
Logs estructurados	✅
JWT	✅
Video de defensa	✅
17. Conclusión

El proyecto implementa una solución distribuida basada en una arquitectura de microservicios utilizando Spring Boot, Spring Cloud y Maven Multi-Módulo.

La aplicación integra descubrimiento de servicios mediante Eureka, un API Gateway como punto único de acceso, autenticación con JWT, documentación automática mediante Swagger/OpenAPI, pruebas unitarias con JUnit 5 y Mockito, logs estructurados y despliegue tanto en modalidad nativa como mediante Docker.

La arquitectura desarrollada permite administrar de manera modular y escalable las operaciones de un taller mecánico y venta de repuestos, cumpliendo los requisitos funcionales y técnicos establecidos para la evaluación.
