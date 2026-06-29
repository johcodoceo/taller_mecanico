# Documentación Swagger/OpenAPI agregada

Se incorporaron anotaciones descriptivas de Swagger/OpenAPI en los controladores principales del proyecto.

## Anotaciones incorporadas

- `@Tag`: clasifica cada controlador por dominio funcional.
- `@Operation`: describe el objetivo de cada endpoint.
- `@ApiResponse`: documenta los principales códigos de respuesta HTTP esperados.

## Controladores documentados

- `AuthController`
- `ClienteController`
- `InventarioController`
- `MecanicoController`
- `OrdenTrabajoController`
- `PagoController`
- `SecurityEventController`
- `UsuarioController`
- `VehiculoController`
- `VentaController`

## Observación técnica

El proyecto ya posee una clase propia llamada `ApiResponse` en los paquetes `util`, por eso las anotaciones Swagger `@ApiResponse` fueron usadas con nombre completamente calificado:

```java
@io.swagger.v3.oas.annotations.responses.ApiResponse(...)
```

Esto evita conflictos de importación con la clase de respuesta propia del proyecto.

## Acceso esperado a Swagger UI

Una vez levantado cada microservicio, Swagger UI debería estar disponible normalmente en:

```text
http://localhost:<PUERTO_DEL_MICROSERVICIO>/swagger-ui.html
```

o también en:

```text
http://localhost:<PUERTO_DEL_MICROSERVICIO>/swagger-ui/index.html
```
