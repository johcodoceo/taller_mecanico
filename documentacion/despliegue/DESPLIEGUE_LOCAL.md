# Despliegue local

## Requisitos

- Java 21 o versión compatible con el proyecto.
- Maven o Maven Wrapper incluido en cada microservicio.
- MySQL mediante XAMPP o Laragon.
- Postman.

## Pasos

1. Iniciar MySQL.
2. Crear las bases de datos indicadas en los `application.properties`.
3. Ejecutar `eureka-server`.
4. Ejecutar `api-gateway`.
5. Ejecutar `auth-service`.
6. Ejecutar los microservicios de negocio.
7. Verificar Eureka en `http://localhost:8761`.
8. Probar endpoints desde Postman usando `http://localhost:8080`.

## Comando de ejecución por servicio

```bash
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd spring-boot:run
```

## Evidencias sugeridas para defensa

- Captura de Eureka con todos los servicios UP.
- Captura de Swagger de 2 o 3 servicios.
- Captura de Postman con login exitoso.
- Captura de request protegido con JWT.
- Captura de validación fallida.
- Captura de creación de orden, venta y pago.
