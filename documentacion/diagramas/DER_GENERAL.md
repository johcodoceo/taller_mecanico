# DER general sugerido

Este DER es una representación lógica general. Cada microservicio mantiene su propia base de datos independiente, por lo que las relaciones entre servicios se manejan mediante IDs y llamadas HTTP, no mediante claves foráneas entre bases distintas.

```mermaid
erDiagram
    AUTH_USER {
        Long id
        String username
        String password
        String rol
    }

    USUARIO {
        Long id
        String nombre
        String apellido
        String correo
        String telefono
        String direccion
        String rut
        Long authId
    }

    CLIENTE {
        Long id
        String nombre
        String apellido
        String rut
        String correo
        String telefono
        String direccion
    }

    VEHICULO {
        Long id
        String patente
        String marca
        String modelo
        Integer anio
        String color
        Long clienteId
    }

    MECANICO {
        Long id
        String nombre
        String apellido
        String rut
        String especialidad
        String telefono
        String correo
        Boolean activo
    }

    ORDEN_TRABAJO {
        Long id
        String diagnostico
        String descripcion
        BigDecimal costoManoObra
        String estado
        Long vehiculoId
        Long mecanicoId
    }

    REPUESTO {
        Long id
        String codigo
        String nombre
        String descripcion
        BigDecimal precio
        Integer stock
        String marca
    }

    VENTA {
        Long id
        Long clienteId
        String metodoPago
        BigDecimal total
        String estado
    }

    VENTA_DETALLE {
        Long id
        Long repuestoId
        Integer cantidad
        BigDecimal precioUnitario
        BigDecimal subtotal
    }

    PAGO {
        Long id
        String metodoPago
        String estadoPago
        Long ordenTrabajoId
        BigDecimal monto
    }

    SECURITY_EVENT {
        Long id
        String username
        String accion
        String descripcion
        String ip
        Boolean exitoso
        LocalDateTime fecha
    }

    CLIENTE ||--o{ VEHICULO : posee
    VEHICULO ||--o{ ORDEN_TRABAJO : genera
    MECANICO ||--o{ ORDEN_TRABAJO : atiende
    CLIENTE ||--o{ VENTA : realiza
    VENTA ||--o{ VENTA_DETALLE : contiene
    REPUESTO ||--o{ VENTA_DETALLE : vendido
    ORDEN_TRABAJO ||--o{ PAGO : genera
```
