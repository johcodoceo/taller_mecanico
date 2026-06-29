# Logs estructurados y trazabilidad

Se incorporaron logs estructurados en formato `clave=valor` para mejorar la trazabilidad operacional de los microservicios.

## Alcance implementado

### 1. Logs HTTP por microservicio

Se agregó el filtro `StructuredRequestLoggingFilter` en los microservicios de negocio:

- `auth-service`
- `cliente-service`
- `inventario-service`
- `mecanico-service`
- `orden-trabajo-service`
- `pago-service`
- `security-service`
- `user-service`
- `vehiculo-service`
- `venta-service`

Este filtro registra:

```text
event=http_request_start method=... path=... remoteAddr=...
event=http_request_complete method=... path=... status=... durationMs=...
```

### 2. Logs de negocio en la capa Service

Se incorporaron logs en operaciones clave:

- Creación de registros.
- Listado de registros.
- Búsqueda por ID o por criterio.
- Actualización de registros.
- Eliminación o anulación de registros.
- Cambios de estado.
- Aumento/descuento de stock.
- Validaciones de relaciones entre microservicios mediante Feign.
- Rechazos por datos inválidos o recursos inexistentes.

Servicios actualizados:

- `AuthService`
- `ClienteService`
- `InventarioService`
- `MecanicoService`
- `OrdenTrabajoService`
- `PagoService`
- `SecurityEventService`
- `UsuarioService`
- `VehiculoService`
- `VentaService`

Ejemplo:

```text
event=orden_create_start vehiculoId=1 mecanicoId=2 costoManoObra=45000
event=orden_create_success ordenId=10 vehiculoId=1 mecanicoId=2 estado=PENDIENTE
```

### 3. Logs de seguridad y JWT

Se reemplazó el uso de `System.out.println` por `Logger` y se agregaron logs en:

- Validación de token JWT.
- Token inválido.
- Autenticación exitosa del filtro JWT.
- Rechazo de solicitudes en API Gateway por token faltante o inválido.

Ejemplo:

```text
event=gateway_auth_rejected reason=missing_bearer_token method=GET path=/api/clientes
event=jwt_filter_authentication_success username=admin rol=ROLE_ADMIN method=GET path=/clientes
```

### 4. Logs de manejo de errores

Se agregaron logs en los `GlobalExceptionHandler` para registrar:

- Recursos no encontrados.
- Errores de solicitud inválida.
- Errores de validación.
- Errores no controlados.

Ejemplo:

```text
event=exception_resource_not_found exception=ResourceNotFoundException message=Cliente no encontrado
event=exception_unhandled exception=NullPointerException message=...
```

### 5. Configuración de patrón de logs

Se agregó configuración en `application.properties` para mostrar logs con datos útiles:

```properties
logging.level.com.taller_mecanico=INFO
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss.SSS} level=%level service=${spring.application.name:-unknown} thread=%thread logger=%logger{36} message="%msg"%n
```

## Resultado del checklist

```text
[x] Integra logs estructurados en puntos clave del flujo del microservicio.
[x] Permite trazabilidad de operaciones HTTP, negocio, seguridad y errores.
[x] Evita registrar datos sensibles como contraseñas o tokens completos.
```
