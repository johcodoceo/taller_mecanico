# Proyecto Taller Mecánico y Venta de Repuestos

Sistema backend basado en arquitectura de microservicios con Spring Boot para gestionar clientes, vehículos, mecánicos, órdenes de trabajo, inventario, ventas, pagos, usuarios, autenticación y auditoría de seguridad.

## 1. Objetivo del sistema

El sistema permite administrar los procesos principales de un taller mecánico y la venta de repuestos:

- Registro y autenticación de usuarios.
- Gestión de clientes.
- Gestión de vehículos asociados a clientes.
- Gestión de mecánicos.
- Creación y seguimiento de órdenes de trabajo.
- Gestión de inventario de repuestos.
- Registro de ventas de repuestos.
- Registro de pagos.
- Registro de eventos de seguridad/auditoría.

## 2. Arquitectura general

El proyecto usa arquitectura de microservicios con:

- Spring Boot.
- Spring Cloud Gateway.
- Eureka Server.
- JWT para autenticación.
- Roles para autorización.
- Swagger/OpenAPI para documentación.
- MySQL como motor de base de datos.
- Response estándar con `ApiResponse<T>`.
- Validaciones con `jakarta.validation`.
- Excepciones globales con `@RestControllerAdvice`.

## 3. Microservicios y puertos

| Servicio | Puerto | Responsabilidad |
|---|---:|---|
| eureka-server | 8761 | Registro y descubrimiento de microservicios |
| api-gateway | 8080 | Punto de entrada único del sistema |
| auth-service | 8081 | Registro, login y generación de JWT |
| user-service | 8082 | Gestión de usuarios internos |
| cliente-service | 8083 | Gestión de clientes |
| vehiculo-service | 8084 | Gestión de vehículos asociados a clientes |
| orden-trabajo-service | 8085 | Gestión de órdenes de trabajo |
| inventario-service | 8086 | Gestión de repuestos y stock |
| pago-service | 8087 | Gestión de pagos |
| mecanico-service | 8088 | Gestión de mecánicos |
| venta-service | 8089 | Gestión de ventas de repuestos |
| security-service | 8090 | Auditoría de eventos de seguridad |

## 4. Roles del sistema

| Rol | Descripción general |
|---|---|
| ADMIN | Acceso administrativo completo |
| OPERADOR | Gestión operativa del taller |
| MECANICO | Consulta y gestión relacionada con órdenes de trabajo |
| CLIENTE | Acceso limitado a información propia o consultas permitidas |

## 5. Orden de ejecución recomendado

1. Iniciar MySQL desde XAMPP o Laragon.
2. Crear las bases de datos necesarias.
3. Levantar `eureka-server`.
4. Levantar `api-gateway`.
5. Levantar `auth-service`.
6. Levantar el resto de microservicios.
7. Verificar que todos aparezcan en Eureka: `http://localhost:8761`.
8. Probar con Postman usando el API Gateway.

## 6. Flujo funcional principal

### Flujo de taller

1. Registrar usuario ADMIN.
2. Iniciar sesión y obtener token JWT.
3. Crear cliente.
4. Crear vehículo asociado al cliente.
5. Crear mecánico.
6. Crear orden de trabajo asociada a vehículo y mecánico.
7. Cambiar estado de la orden.
8. Crear pago asociado a la orden.

### Flujo de venta de repuestos

1. Crear cliente.
2. Crear repuesto en inventario.
3. Crear venta asociada a cliente y repuesto.
4. Descontar stock del inventario.
5. Registrar pago de la venta.

### Flujo de seguridad

1. Registrar login exitoso o fallido.
2. Consultar eventos de seguridad.
3. Consultar eventos fallidos.

## 7. Swagger

Cada microservicio de negocio cuenta con Swagger disponible en:

```text
http://localhost:PUERTO/swagger-ui/index.html
```

Ejemplos:

```text
http://localhost:8081/swagger-ui/index.html
http://localhost:8083/swagger-ui/index.html
http://localhost:8088/swagger-ui/index.html
```

## 8. Postman

Se incluye una colección Postman en:

```text
documentacion/postman/Taller_Mecanico_Microservicios.postman_collection.json
```

Variables recomendadas:

| Variable | Valor inicial |
|---|---|
| gateway_url | http://localhost:8080 |
| token_admin | Token obtenido en login |

## 9. Pruebas

Cada microservicio incluye pruebas base de carga de contexto y pruebas simples sobre `ApiResponse`.

Para ejecutar pruebas en un servicio:

```bash
cd cliente-service/cliente-service
./mvnw test
```

En Windows:

```bash
mvnw.cmd test
```

## 10. Repositorio GitHub

Recomendación de ramas:

```text
main
develop
feature/auth-service
feature/cliente-service
feature/vehiculo-service
feature/madurez-swagger
feature/seguridad-roles
```

Recomendación de commits:

```text
git add .
git commit -m "Agrega response estándar y manejo global de excepciones"
git commit -m "Implementa seguridad por roles en microservicios"
git commit -m "Agrega documentación y colección Postman"
```
