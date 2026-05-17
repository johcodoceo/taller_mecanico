# Documentación de arquitectura

## Tipo de arquitectura

El sistema utiliza una arquitectura de microservicios. Cada servicio tiene una responsabilidad funcional clara, expone endpoints REST y mantiene independencia lógica respecto de los demás servicios.

## Componentes principales

- **Eureka Server:** registro dinámico de servicios.
- **API Gateway:** punto único de entrada para Postman, frontend u otros consumidores.
- **Auth Service:** autenticación y emisión de tokens JWT.
- **Servicios de negocio:** clientes, vehículos, mecánicos, órdenes, inventario, ventas, pagos y usuarios.
- **Security Service:** auditoría de acciones o eventos sensibles.

## Comunicación entre microservicios

La comunicación se realiza mediante HTTP y Feign Client. Cuando un servicio necesita validar o consultar información externa, realiza llamadas al microservicio responsable.

Ejemplos:

- `vehiculo-service` consulta `cliente-service` para validar existencia de cliente.
- `pago-service` consulta `orden-trabajo-service` para validar órdenes de trabajo.
- `venta-service` consulta `inventario-service` para validar y descontar stock.

## Seguridad

El sistema usa JWT. El token se genera en `auth-service` y luego se envía en cada solicitud protegida mediante el header:

```text
Authorization: Bearer TOKEN
```

Los endpoints se protegen mediante roles:

- `ADMIN`
- `OPERADOR`
- `MECANICO`
- `CLIENTE`

## Response estándar

Las APIs devuelven respuestas usando la estructura:

```json
{
  "success": true,
  "message": "Operación realizada correctamente",
  "data": {}
}
```

En caso de error:

```json
{
  "success": false,
  "message": "Mensaje del error",
  "data": null
}
```

## Validaciones

Los DTOs usan anotaciones de validación como:

- `@NotBlank`
- `@NotNull`
- `@Email`
- `@Min`
- `@Positive`

## Excepciones globales

Cada microservicio cuenta con `GlobalExceptionHandler`, encargado de centralizar respuestas de error y evitar que los controllers manejen errores repetidamente.
