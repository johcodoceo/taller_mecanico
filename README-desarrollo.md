# 🚀 SISTEMA DE MICROSERVICIOS MULTIMÓDULO - ENTREGA FINAL

📦 COMPONENTES DE DISTRIBUCIÓN Y DEFENSA TÉCNICA

Utilice los siguientes enlaces externos para descargar las versiones listas para producción y visualizar la defensa del proyecto:

| Componente | Descripción | Enlace de Descarga (Nube externa) |
| :--- | :--- | :--- |
| 📦 Versión Sin Docker <br>(Arranque Nativo) | Archivo .zip que contiene la carpeta apps/ con los .jar compilados y el script arrancar-nativo.bat ordenado por fases. | [Descargar ZIP Nativo aquí](ENLACE_A_DRIVE_AQUÍ) |
| 🐳 Versión Con Docker <br>(Avance Examen Transversal) | Archivo .zip que contiene la carpeta apps/ con los .jar, el archivo docker-compose.yml y el script automatizado arrancar-sistema.bat. | [Descargar ZIP Docker aquí](ENLACE_A_DRIVE_AQUÍ) |
| 🎥 Video de Defensa Técnica <br>(Evaluación Individual) | Enlace directo al video explicativo donde se evidencia el funcionamiento, testing y el aporte técnico individual. Duración ideal: 15 minutos (Máximo permitido: 18 minutos). | [Ver Video Explicativo aquí](ENLACE_A_VIDEO_AQUÍ) |

---

# Sistema de Taller Mecánico y Venta de Repuestos

Proyecto backend desarrollado con **Java 17**, **Spring Boot 3.5.14** y **Spring Cloud 2025.0.2**, basado en arquitectura de **microservicios**. El sistema permite administrar las operaciones principales de un taller mecánico, incluyendo clientes, vehículos, mecánicos, órdenes de trabajo, inventario, ventas, pagos, usuarios, autenticación y trazabilidad de eventos de seguridad.

El proyecto utiliza un **POM padre** con empaquetado `pom`, desde donde se centralizan módulos, versiones y dependencias comunes. Cada microservicio mantiene su propia responsabilidad, puerto, configuración y base de datos.

---

## 1. Objetivo general del sistema

El objetivo del sistema es entregar una solución backend modular para la gestión integral de un taller mecánico y venta de repuestos, permitiendo registrar, consultar, actualizar y controlar las operaciones del negocio mediante servicios independientes, documentados y comunicados a través de un API Gateway.

---

## 2. Objetivos específicos

- Centralizar el acceso al sistema mediante un **API Gateway**.
- Registrar y autenticar usuarios mediante **JWT**.
- Gestionar usuarios internos del sistema.
- Registrar clientes del taller.
- Asociar vehículos a clientes.
- Gestionar mecánicos activos e inactivos.
- Crear y consultar órdenes de trabajo.
- Actualizar estados de órdenes de trabajo.
- Administrar inventario de repuestos.
- Registrar ventas de repuestos.
- Registrar pagos asociados a operaciones del sistema.
- Registrar eventos de seguridad y auditoría.
- Documentar endpoints mediante **Swagger/OpenAPI**.
- Incorporar pruebas unitarias con **JUnit y Mockito**.
- Incorporar logs estructurados para trazabilidad operacional.

---

## 3. Función de la aplicación

La aplicación permite controlar el flujo principal de atención de un taller mecánico:

1. Un usuario se registra o inicia sesión.
2. El sistema genera un token JWT para consumir rutas protegidas.
3. Se registra un cliente.
4. Se registra uno o más vehículos asociados al cliente.
5. Se registra un mecánico responsable.
6. Se crea una orden de trabajo para el vehículo.
7. Se actualiza el estado de la orden según avance el servicio.
8. Se registra el pago correspondiente.
9. Se registran eventos relevantes de seguridad y operación.

También permite gestionar un flujo de venta de repuestos:

1. Se registra un repuesto en inventario.
2. Se consulta stock disponible.
3. Se realiza una venta.
4. Se descuenta stock.
5. Se registra el pago.
6. Se puede anular la venta si corresponde.

---

## 4. Tecnologías utilizadas

| Tecnología | Uso dentro del proyecto |
|---|---|
| Java 17 | Lenguaje principal del sistema |
| Spring Boot 3.5.14 | Framework principal para microservicios |
| Spring Cloud 2025.0.2 | Integración con Eureka y Gateway |
| Spring Cloud Netflix Eureka | Registro y descubrimiento de servicios |
| Spring Cloud Gateway | Punto de entrada único a los microservicios |
| Spring Web / WebFlux | Exposición de APIs REST y Gateway reactivo |
| Spring Data JPA | Persistencia de datos |
| MySQL | Base de datos relacional |
| JWT | Autenticación basada en tokens |
| Spring Security | Seguridad y control de acceso |
| Swagger / OpenAPI | Documentación de endpoints |
| JUnit 5 | Pruebas unitarias |
| Mockito | Simulación de dependencias en pruebas |
| SLF4J / Logback | Logs estructurados |
| Maven | Gestión de dependencias y compilación |

---

## 5. Arquitectura general de microservicios

El sistema está compuesto por un servidor de descubrimiento, un gateway y varios microservicios de negocio.

```text
Cliente / Postman / Frontend
        |
        v
API Gateway - puerto 8080
        |
        v
Eureka Server - puerto 8761
        |
        +--> auth-service
        +--> user-service
        +--> cliente-service
        +--> vehiculo-service
        +--> orden-trabajo-service
        +--> inventario-service
        +--> pago-service
        +--> mecanico-service
        +--> venta-service
        +--> security-service
```

Cada microservicio se registra en Eureka y puede ser alcanzado mediante el Gateway o directamente por su puerto local durante pruebas de desarrollo.

---

## 6. Tabla de microservicios y puertos

| N° | Servicio | Puerto | Base de datos | Responsabilidad principal |
|---:|---|---:|---|---|
| 1 | `eureka-server` | `8761` | No aplica | Registro y descubrimiento de microservicios |
| 2 | `api-gateway` | `8080` | No aplica | Entrada única al sistema y enrutamiento de peticiones |
| 3 | `auth-service` | `8081` | `db_auth` | Registro, login y generación de token JWT |
| 4 | `user-service` | `8082` | `db_users` | Gestión de usuarios internos |
| 5 | `cliente-service` | `8083` | `db_clientes` | Gestión de clientes |
| 6 | `vehiculo-service` | `8084` | `db_vehiculos` | Gestión de vehículos asociados a clientes |
| 7 | `orden-trabajo-service` | `8085` | `db_ordenes` | Gestión de órdenes de trabajo |
| 8 | `inventario-service` | `8086` | `db_inventario` | Gestión de repuestos y stock |
| 9 | `pago-service` | `8087` | `db_pagos` | Registro y consulta de pagos |
| 10 | `mecanico-service` | `8088` | `db_mecanicos` | Gestión de mecánicos |
| 11 | `venta-service` | `8089` | `db_ventas` | Gestión de ventas de repuestos |
| 12 | `security-service` | `8090` | `db_security` | Registro y consulta de eventos de seguridad |

---

## 7. Arquitectura de capas

Cada microservicio de negocio sigue una arquitectura por capas, separando responsabilidades para facilitar mantenimiento, pruebas y escalabilidad.

```text
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
Entity / Base de datos
```

### 7.1 Capa Controller

La capa `controller` expone los endpoints REST. Su responsabilidad es recibir solicitudes HTTP, validar entrada básica, invocar la lógica de negocio y devolver respuestas al cliente.

Ejemplos:

```text
ClienteController
VehiculoController
OrdenTrabajoController
PagoController
VentaController
```

En esta capa también se incorporan anotaciones Swagger/OpenAPI como:

```java
@Tag
@Operation
@ApiResponse
```

Estas anotaciones permiten documentar el comportamiento de los endpoints principales, sus respuestas esperadas y códigos HTTP.

### 7.2 Capa Service

La capa `service` contiene la lógica de negocio. Aquí se procesan reglas como creación, actualización, búsqueda, eliminación, cambio de estado, validación de stock, registro de pagos o autenticación.

También se integran logs estructurados en puntos clave del flujo, por ejemplo:

```text
Inicio de operación
Registro creado
Entidad no encontrada
Actualización realizada
Error controlado
Operación finalizada
```

### 7.3 Capa Repository

La capa `repository` utiliza Spring Data JPA para acceder a la base de datos. Define interfaces que extienden `JpaRepository` y permiten realizar operaciones CRUD.

Ejemplo:

```java
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
```

### 7.4 Capa Entity

La capa `entity` representa las tablas de la base de datos mediante clases Java anotadas con JPA.

Ejemplo:

```java
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
```

### 7.5 Capa DTO

La capa `dto` se utiliza para transportar datos entre cliente y servidor sin exponer directamente las entidades internas.

Ejemplos:

```text
LoginRequest
RegisterRequest
```

### 7.6 Capa Exception

La capa `exception` centraliza el manejo de errores mediante `GlobalExceptionHandler` y excepciones personalizadas.

Permite responder de manera uniforme ante casos como:

```text
Recurso no encontrado
Datos inválidos
Errores de validación
Errores internos del servidor
```

### 7.7 Capa Config y Security

Las capas `config` y `security` contienen configuración transversal del sistema, como Swagger, JWT, Spring Security y filtros de autenticación.

---

## 8. Estructura general del proyecto

```text
taller_mecanico-main/
│
├── pom.xml
├── README.md
├── arrancar-nativo.bat
│
├── eureka-server/
├── api-gateway/
├── auth-service/
├── user-service/
├── cliente-service/
├── vehiculo-service/
├── orden-trabajo-service/
├── inventario-service/
├── pago-service/
├── mecanico-service/
├── venta-service/
├── security-service/
│
├── docs/
├── README_MOCKITO.md
├── README_SWAGGER_OPENAPI.md
└── README_LOGS_ESTRUCTURADOS.md
```

---

## 9. Módulos declarados en el POM padre

El proyecto principal usa un `pom.xml` padre con los siguientes módulos:

```xml
<modules>
    <module>eureka-server</module>
    <module>api-gateway</module>
    <module>auth-service</module>
    <module>security-service</module>
    <module>user-service</module>
    <module>cliente-service</module>
    <module>vehiculo-service</module>
    <module>mecanico-service</module>
    <module>inventario-service</module>
    <module>orden-trabajo-service</module>
    <module>pago-service</module>
    <module>venta-service</module>
</modules>
```

Esto permite ejecutar comandos Maven globales desde la carpeta raíz del proyecto.

---

## 10. Endpoints principales por microservicio

### 10.1 Auth Service

Base local:

```text
http://localhost:8081/auth
```

| Método | Endpoint | Función |
|---|---|---|
| POST | `/auth/register` | Registrar usuario |
| POST | `/auth/login` | Iniciar sesión y obtener JWT |

### 10.2 User Service

Base local:

```text
http://localhost:8082/users
```

| Método | Endpoint | Función |
|---|---|---|
| POST | `/users` | Crear usuario |
| GET | `/users` | Listar usuarios |
| GET | `/users/{id}` | Buscar usuario por ID |
| PUT | `/users/{id}` | Actualizar usuario |
| DELETE | `/users/{id}` | Eliminar usuario |

### 10.3 Cliente Service

Base local:

```text
http://localhost:8083/clientes
```

| Método | Endpoint | Función |
|---|---|---|
| POST | `/clientes` | Crear cliente |
| GET | `/clientes` | Listar clientes |
| GET | `/clientes/{id}` | Buscar cliente por ID |
| GET | `/clientes/rut/{rut}` | Buscar cliente por RUT |
| PUT | `/clientes/{id}` | Actualizar cliente |
| DELETE | `/clientes/{id}` | Eliminar cliente |

### 10.4 Vehículo Service

Base local:

```text
http://localhost:8084/vehiculos
```

| Método | Endpoint | Función |
|---|---|---|
| POST | `/vehiculos` | Crear vehículo |
| GET | `/vehiculos` | Listar vehículos |
| GET | `/vehiculos/cliente/{clienteId}` | Listar vehículos por cliente |
| GET | `/vehiculos/{id}` | Buscar vehículo por ID |

### 10.5 Orden Trabajo Service

Base local:

```text
http://localhost:8085/ordenes
```

| Método | Endpoint | Función |
|---|---|---|
| POST | `/ordenes` | Crear orden de trabajo |
| GET | `/ordenes` | Listar órdenes de trabajo |
| GET | `/ordenes/{id}` | Buscar orden por ID |
| PUT | `/ordenes/{id}/estado` | Actualizar estado de orden |

### 10.6 Inventario Service

Base local:

```text
http://localhost:8086/inventario
```

| Método | Endpoint | Función |
|---|---|---|
| POST | `/inventario` | Crear repuesto o producto |
| GET | `/inventario` | Listar inventario |
| PUT | `/inventario/{id}/aumentar` | Aumentar stock |
| PUT | `/inventario/{id}/descontar` | Descontar stock |

### 10.7 Pago Service

Base local:

```text
http://localhost:8087/pagos
```

| Método | Endpoint | Función |
|---|---|---|
| POST | `/pagos` | Registrar pago |
| GET | `/pagos` | Listar pagos |

### 10.8 Mecánico Service

Base local:

```text
http://localhost:8088/mecanicos
```

| Método | Endpoint | Función |
|---|---|---|
| POST | `/mecanicos` | Crear mecánico |
| GET | `/mecanicos` | Listar mecánicos |
| GET | `/mecanicos/activos` | Listar mecánicos activos |
| GET | `/mecanicos/{id}` | Buscar mecánico por ID |
| PUT | `/mecanicos/{id}` | Actualizar mecánico |
| PUT | `/mecanicos/{id}/desactivar` | Desactivar mecánico |
| PUT | `/mecanicos/{id}/reactivar` | Reactivar mecánico |

### 10.9 Venta Service

Base local:

```text
http://localhost:8089/ventas
```

| Método | Endpoint | Función |
|---|---|---|
| POST | `/ventas` | Crear venta |
| GET | `/ventas` | Listar ventas |
| GET | `/ventas/{id}` | Buscar venta por ID |
| GET | `/ventas/cliente/{clienteId}` | Listar ventas por cliente |
| PUT | `/ventas/{id}/anular` | Anular venta |

### 10.10 Security Service

Base local:

```text
http://localhost:8090/security-events
```

| Método | Endpoint | Función |
|---|---|---|
| POST | `/security-events` | Registrar evento de seguridad |
| GET | `/security-events` | Listar eventos de seguridad |
| GET | `/security-events/{id}` | Buscar evento por ID |
| GET | `/security-events/usuario/{username}` | Listar eventos por usuario |
| GET | `/security-events/fallidos` | Listar eventos fallidos |

---

## 11. Swagger/OpenAPI

Cada microservicio de negocio incluye documentación Swagger/OpenAPI mediante anotaciones:

```java
@Tag
@Operation
@ApiResponse
```

La documentación se encuentra disponible en:

```text
http://localhost:PUERTO/swagger-ui/index.html
```

Ejemplos:

| Servicio | URL Swagger |
|---|---|
| Auth | `http://localhost:8081/swagger-ui/index.html` |
| User | `http://localhost:8082/swagger-ui/index.html` |
| Cliente | `http://localhost:8083/swagger-ui/index.html` |
| Vehículo | `http://localhost:8084/swagger-ui/index.html` |
| Orden Trabajo | `http://localhost:8085/swagger-ui/index.html` |
| Inventario | `http://localhost:8086/swagger-ui/index.html` |
| Pago | `http://localhost:8087/swagger-ui/index.html` |
| Mecánico | `http://localhost:8088/swagger-ui/index.html` |
| Venta | `http://localhost:8089/swagger-ui/index.html` |
| Security | `http://localhost:8090/swagger-ui/index.html` |

---

## 12. Logs estructurados

El sistema incorpora logs estructurados para trazabilidad operacional en puntos clave del flujo de cada microservicio.

Formato general configurado:

```properties
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss.SSS} level=%level service=${spring.application.name:-unknown} thread=%thread logger=%logger{36} message="%msg"%n
```

Ejemplo de salida esperada:

```text
2026-06-29 10:30:15.120 level=INFO service=cliente-service thread=http-nio-8083-exec-1 logger=c.t.c.service.ClienteService message="Creando cliente rut=12345678-9"
```

Los logs se registran en puntos como:

- Inicio de operaciones.
- Creación de registros.
- Actualización de registros.
- Eliminación o desactivación.
- Búsqueda por ID.
- Recursos no encontrados.
- Errores controlados.
- Validación y rechazo de JWT.
- Registro de método HTTP, ruta, estado y duración de request.

---

## 13. Pruebas unitarias con Mockito

El proyecto incorpora pruebas unitarias con **JUnit 5** y **Mockito**. Las dependencias de testing están centralizadas en el `pom.xml` padre.

Ejemplo de prueba con Mockito:

```java
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;
}
```

Esto permite probar la lógica de negocio sin conectarse a la base de datos real.

---

## 14. Requisitos previos

Antes de ejecutar el proyecto se recomienda tener instalado:

| Herramienta | Versión recomendada |
|---|---|
| Java JDK | 17 |
| Maven | 3.9.x o superior |
| MySQL | 8.x o compatible |
| IDE | IntelliJ IDEA, Eclipse o VS Code |
| Postman | Opcional para pruebas REST |

---

## 15. Configuración de base de datos

Los microservicios usan MySQL en el puerto `3307` según sus archivos `application.properties`.

Bases de datos esperadas:

```sql
CREATE DATABASE db_auth;
CREATE DATABASE db_users;
CREATE DATABASE db_clientes;
CREATE DATABASE db_vehiculos;
CREATE DATABASE db_ordenes;
CREATE DATABASE db_inventario;
CREATE DATABASE db_pagos;
CREATE DATABASE db_mecanicos;
CREATE DATABASE db_ventas;
CREATE DATABASE db_security;
```

El archivo general de base de datos se encuentra en:

```text
docs/bd-general.sql
```

---

## 16. Orden recomendado de ejecución

Para levantar correctamente la arquitectura distribuida, se recomienda este orden:

1. Iniciar MySQL.
2. Crear las bases de datos necesarias.
3. Ejecutar `eureka-server`.
4. Ejecutar los microservicios de negocio:
   - `auth-service`
   - `user-service`
   - `cliente-service`
   - `vehiculo-service`
   - `orden-trabajo-service`
   - `inventario-service`
   - `pago-service`
   - `mecanico-service`
   - `venta-service`
   - `security-service`
5. Ejecutar `api-gateway`.
6. Verificar Eureka en:

```text
http://localhost:8761
```

7. Probar los endpoints desde Postman o Swagger.

---

## 17. Comandos globales de Maven

Como el proyecto tiene un `pom.xml` padre, los comandos Maven pueden ejecutarse desde la raíz:

```bash
cd taller_mecanico-main
```

### 17.1 Limpiar y compilar todo el proyecto

```bash
mvn clean install
```

Este comando:

- Limpia archivos generados anteriormente.
- Compila todos los módulos.
- Ejecuta las pruebas.
- Instala los artefactos en el repositorio local de Maven.

### 17.2 Compilar todo sin ejecutar pruebas

```bash
mvn clean install -DskipTests
```

Útil cuando se quiere validar compilación, pero no ejecutar pruebas unitarias.

### 17.3 Ejecutar solo pruebas

```bash
mvn test
```

### 17.4 Limpiar proyecto

```bash
mvn clean
```

### 17.5 Compilar sin instalar

```bash
mvn clean package
```

### 17.6 Compilar un microservicio específico y sus dependencias

```bash
mvn -pl auth-service -am clean install
```

Ejemplos:

```bash
mvn -pl cliente-service -am clean install
mvn -pl orden-trabajo-service -am clean install
mvn -pl venta-service -am clean install
```

### 17.7 Ejecutar pruebas de un microservicio específico

```bash
mvn -pl auth-service test
```

Ejemplos:

```bash
mvn -pl cliente-service test
mvn -pl pago-service test
mvn -pl inventario-service test
```

### 17.8 Reanudar compilación desde un módulo con error

Si Maven falla en un módulo específico, se puede continuar desde ese módulo usando:

```bash
mvn clean install -rf :auth-service
```

Ejemplo:

```bash
mvn clean install -rf :cliente-service
```

---

## 18. Ejecución individual de microservicios

Desde la raíz del proyecto, cada microservicio puede ejecutarse con Maven indicando el módulo:

```bash
mvn -pl eureka-server spring-boot:run
```

Ejemplos:

```bash
mvn -pl auth-service spring-boot:run
mvn -pl cliente-service spring-boot:run
mvn -pl vehiculo-service spring-boot:run
mvn -pl orden-trabajo-service spring-boot:run
mvn -pl api-gateway spring-boot:run
```

También se puede ingresar a cada carpeta y ejecutar:

```bash
mvn spring-boot:run
```

---

## 19. Acceso mediante API Gateway

El API Gateway se ejecuta en:

```text
http://localhost:8080
```

El Gateway tiene habilitado el descubrimiento por Eureka:

```properties
spring.cloud.gateway.discovery.locator.enabled=true
spring.cloud.gateway.discovery.locator.lower-case-service-id=true
```

Además, incluye rutas explícitas para servicios como:

```text
/mecanicos/**
/ventas/**
/security-events/**
```

---

## 20. Seguridad y autenticación

El sistema utiliza JWT para autenticación.

Flujo básico:

1. Registrar usuario en `auth-service`.
2. Iniciar sesión en `/auth/login`.
3. Obtener token JWT.
4. Enviar token en las solicitudes protegidas usando el header:

```http
Authorization: Bearer TOKEN_JWT
```

---

## 21. Documentación complementaria incluida

El proyecto también incluye archivos específicos de documentación:

| Archivo | Contenido |
|---|---|
| `README_MOCKITO.md` | Explicación de pruebas unitarias con Mockito |
| `README_SWAGGER_OPENAPI.md` | Resumen de documentación Swagger/OpenAPI |
| `README_LOGS_ESTRUCTURADOS.md` | Descripción de logs estructurados |
| `docs/bd-general.sql` | Script general de base de datos |
| `docs/endpoints.md` | Documentación de endpoints |

---

## 22. Checklist de cumplimiento técnico

| Requisito | Estado |
|---|---|
| Arquitectura de microservicios | Cumple |
| POM padre con módulos Maven | Cumple |
| Eureka Server | Cumple |
| API Gateway | Cumple |
| Microservicios de negocio separados | Cumple |
| Bases de datos por servicio | Cumple |
| Swagger/OpenAPI documentado | Cumple |
| Mockito y pruebas unitarias | Cumple |
| Logs estructurados | Cumple |
| Manejo global de excepciones | Cumple |
| Comandos Maven globales documentados | Cumple |
| Tabla de puertos documentada | Cumple |
| Arquitectura de capas documentada | Cumple |

---

## 23. Resumen final

Este sistema implementa una solución backend distribuida para un taller mecánico y venta de repuestos. La arquitectura está organizada en microservicios independientes, registrados en Eureka, accesibles mediante API Gateway, documentados con Swagger/OpenAPI, probados con Mockito y trazados mediante logs estructurados.

La estructura basada en POM padre permite ejecutar comandos globales de Maven como:

```bash
mvn clean install
```

desde la raíz del proyecto, facilitando la compilación, pruebas y validación de todos los módulos.
