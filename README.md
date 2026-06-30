
  # 🚀 SISTEMA DE MICROSERVICIOS MULTIMÓDULO - ENTREGA FINAL


## 📦 COMPONENTES DE DISTRIBUCIÓN Y DEFENSA TÉCNICA

Utilice los siguientes enlaces externos para descargar las versiones listas para producción y visualizar la defensa del proyecto:



| Componente | Descripción | Enlace de Descarga (Nube externa) |

| :--- | :--- | :--- |

| **📦 Versión Sin Docker** <br>*(Arranque Nativo)* | Archivo `.zip` que contiene la carpeta `apps/` con los `.jar` compilados y el script `arrancar-nativo.bat` ordenado por fases. | [Descargar ZIP Nativo aquí](https://drive.google.com/file/d/1azkHPlvBv49VBWWkD2a84scpTL6op_9o/view?usp=sharing) |

| **🐳 Versión Con Docker** <br>*(Avance Examen Transversal)* | Archivo `.zip` que contiene la carpeta `apps/` con los `.jar`, el archivo `docker-compose.yml` y el script automatizado `arrancar-sistema.bat`. | [Descargar ZIP Docker aquí](https://drive.google.com/file/d/1pQeJgMBx8Pb-p990V4KgjTFmTtvPplcA/view?usp=sharing) |

| **🎥 Video de Defensa Técnica** <br>*(Evaluación Individual)* | Enlace directo al video explicativo donde se evidencia el funcionamiento, testing y el aporte técnico individual. **Duración ideal: 15 minutos (Máximo permitido: 18 minutos).** | [Ver Video Explicativo aquí](https://drive.google.com/drive/folders/1TwkMSGTVrnF7GZKkhrhVfHe2NaTwxP6j?usp=sharing) |


---

# 📦 Distribución del Proyecto

Los enlaces de descarga y material complementario se encuentran disponibles en la sección de entrega correspondiente:

* Versión Nativa (.jar + script .bat)
* Versión Docker
* Video de defensa técnica
* Archivo de subtítulos del video

---

# 1. Descripción del Proyecto

Sistema backend desarrollado mediante una arquitectura de microservicios utilizando Java 17 y Spring Boot.

La aplicación permite gestionar las operaciones principales de un taller mecánico y una tienda de repuestos mediante servicios independientes, desacoplados y documentados.

El sistema permite:

* Gestión de usuarios.
* Autenticación JWT.
* Gestión de clientes.
* Registro de vehículos.
* Administración de mecánicos.
* Órdenes de trabajo.
* Inventario.
* Ventas.
* Pagos.
* Eventos de seguridad.
* Auditoría y trazabilidad.

---

# 2. Objetivo del Sistema

Desarrollar una solución distribuida basada en microservicios que permita administrar integralmente las operaciones de un taller mecánico y venta de repuestos.

---

# 3. Tecnologías Utilizadas

| Tecnología      | Uso                  |
| --------------- | -------------------- |
| Java 17         | Lenguaje principal   |
| Spring Boot 3   | Framework            |
| Spring Cloud    | Microservicios       |
| Eureka Server   | Descubrimiento       |
| API Gateway     | Enrutamiento         |
| Spring Security | Seguridad            |
| JWT             | Autenticación        |
| Spring Data JPA | Persistencia         |
| MySQL           | Base de datos        |
| Swagger/OpenAPI | Documentación        |
| JUnit 5         | Testing              |
| Mockito         | Mocking              |
| SLF4J / Logback | Logs                 |
| Maven           | Gestión del proyecto |
| Docker          | Contenedores         |

---

# 4. Arquitectura General

```text
Cliente / Postman
        |
        ▼
API Gateway (8080)
        |
        ▼
Eureka Server (8761)
        |
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

Cada servicio posee:

* Base de datos independiente.
* Responsabilidad única.
* Registro en Eureka.
* Documentación Swagger.
* Pruebas unitarias.
* Logs estructurados.

---

# 5. Microservicios del Sistema

| Servicio              | Puerto | Función               |
| --------------------- | ------ | --------------------- |
| Eureka Server         | 8761   | Registro de servicios |
| API Gateway           | 8080   | Punto de acceso       |
| Auth Service          | 8081   | JWT y autenticación   |
| User Service          | 8082   | Usuarios              |
| Cliente Service       | 8083   | Clientes              |
| Vehículo Service      | 8084   | Vehículos             |
| Orden Trabajo Service | 8085   | Órdenes               |
| Inventario Service    | 8086   | Repuestos             |
| Pago Service          | 8087   | Pagos                 |
| Mecánico Service      | 8088   | Mecánicos             |
| Venta Service         | 8089   | Ventas                |
| Security Service      | 8090   | Auditoría             |

---

# 6. Arquitectura por Capas

Todos los microservicios utilizan una arquitectura basada en capas.

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Entity
```

Capas implementadas:

* Controller.
* Service.
* Repository.
* Entity.
* DTO.
* Exception.
* Config.
* Security.

Esta separación facilita:

* Escalabilidad.
* Mantenibilidad.
* Testing.
* Reutilización.

---

# 7. Ejecución Nativa del Sistema

El proyecto incorpora el script:

```text
arrancar-nativo.bat
```

Este archivo automatiza el orden de inicio exigido por la evaluación.

## Orden de arranque

1. Eureka Server.
2. Microservicios.
3. API Gateway.

El orden jerárquico garantiza el correcto registro de servicios y la disponibilidad del sistema.

---

# 8. Ejecución Manual

## Eureka Server

```bash
mvn -pl eureka-server spring-boot:run
```

## Microservicios

```bash
mvn -pl auth-service spring-boot:run
mvn -pl cliente-service spring-boot:run
mvn -pl vehiculo-service spring-boot:run
mvn -pl mecanico-service spring-boot:run
mvn -pl inventario-service spring-boot:run
mvn -pl orden-trabajo-service spring-boot:run
mvn -pl pago-service spring-boot:run
mvn -pl venta-service spring-boot:run
mvn -pl security-service spring-boot:run
mvn -pl user-service spring-boot:run
```

## API Gateway

```bash
mvn -pl api-gateway spring-boot:run
```

---

# 9. Compilación del Proyecto

Desde la raíz del proyecto:

```bash
mvn clean install
```

Este comando:

* Limpia el proyecto.
* Compila todos los módulos.
* Ejecuta pruebas unitarias.
* Genera los artefactos.

Otros comandos:

```bash
mvn test
mvn clean package
mvn clean install -DskipTests
```

---

# 10. Pruebas Unitarias

El sistema incorpora pruebas unitarias mediante:

* JUnit 5.
* Mockito.

Las dependencias se encuentran centralizadas en el POM padre.

Servicios con pruebas:

* Auth Service.
* Cliente Service.
* User Service.
* Vehículo Service.
* Mecánico Service.
* Inventario Service.
* Orden Trabajo Service.
* Pago Service.
* Venta Service.
* Security Service.

Ejemplo:

```java
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;
}
```

Las pruebas pueden ejecutarse mediante:

```bash
mvn test
```

o:

```bash
mvn clean install
```

---

# 11. Documentación Swagger/OpenAPI

Se implementó documentación de endpoints utilizando:

* @Tag
* @Operation
* @ApiResponse

Controladores documentados:

* AuthController
* ClienteController
* InventarioController
* VehiculoController
* UsuarioController
* PagoController
* VentaController
* SecurityEventController
* MecanicoController
* OrdenTrabajoController

Acceso:

```text
http://localhost:8081/swagger-ui/index.html
```

---

# 12. Logs Estructurados

Se implementaron logs estructurados para trazabilidad del sistema.

Eventos registrados:

* Inicio de solicitudes.
* Finalización de solicitudes.
* Operaciones de negocio.
* Validaciones.
* Errores.
* Seguridad JWT.
* Manejo de excepciones.

Ejemplo:

```text
event=http_request_complete
method=POST
path=/clientes
status=201
durationMs=45
```

Configuración:

```properties
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss.SSS}
level=%level
service=${spring.application.name}
message="%msg"
```

---

# 13. Seguridad

El sistema utiliza autenticación basada en JWT.

Flujo:

1. Registro de usuario.
2. Inicio de sesión.
3. Generación de token.
4. Envío del token mediante:

```http
Authorization: Bearer TOKEN
```

Los eventos de seguridad son registrados por Security Service.

---

# 14. Docker

La entrega incluye una versión Docker del sistema.

La distribución contiene:

* docker-compose.yml
* imágenes de los servicios
* archivos de configuración

Esto permite desplegar la arquitectura completa mediante contenedores.

---

# 15. Video de Defensa

La entrega incluye un video de defensa técnica.

Características:

* Duración ideal: 15 minutos.
* Duración máxima: 18 minutos.
* Demostración del sistema.
* Explicación técnica.
* Evidencia de pruebas.
* Evidencia de Swagger.
* Evidencia de logs.

---

# 16. Subtítulos y Accesibilidad

Se incluye el archivo:

```text
subtitulos-video.txt
```

Este archivo contiene la transcripción del video de defensa, cumpliendo los requisitos de accesibilidad de la evaluación.

---

# 17. Requisitos del Sistema

| Herramienta | Versión  |
| ----------- | -------- |
| Java        | 17       |
| Maven       | 3.9+     |
| MySQL       | 8        |
| Docker      | Última   |
| Postman     | Opcional |

---

# 18. Checklist de Cumplimiento

| Requisito                      | Estado |
| ------------------------------ | ------ |
| Arquitectura de microservicios | ✔      |
| POM Padre                      | ✔      |
| Eureka Server                  | ✔      |
| API Gateway                    | ✔      |
| Microservicios independientes  | ✔      |
| Bases de datos separadas       | ✔      |
| Swagger/OpenAPI                | ✔      |
| JUnit 5                        | ✔      |
| Mockito                        | ✔      |
| Logs estructurados             | ✔      |
| JWT                            | ✔      |
| Script de arranque             | ✔      |
| Versión Docker                 | ✔      |
| Video de defensa               | ✔      |
| Subtítulos del video           | ✔      |

---

# 19. Conclusión

El sistema implementa una arquitectura de microservicios basada en Spring Boot y Spring Cloud, aplicando principios de desacoplamiento, mantenibilidad y escalabilidad.

La solución integra:

* Descubrimiento de servicios.
* API Gateway.
* Seguridad JWT.
* Swagger/OpenAPI.
* Pruebas unitarias.
* Logs estructurados.
* Docker.
* Automatización del arranque.

La arquitectura desarrollada permite administrar las operaciones de un taller mecánico y venta de repuestos mediante servicios independientes y documentados.
