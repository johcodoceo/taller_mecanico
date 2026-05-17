# Guía de pruebas en Postman

## Variables de entorno recomendadas

| Variable | Valor |
|---|---|
| gateway_url | http://localhost:8080 |
| token_admin | Pegar token obtenido en login |

## Flujo completo con ADMIN

### 1. Registrar ADMIN

```http
POST {{gateway_url}}/auth-service/auth/register
```

```json
{
  "username": "admin",
  "password": "123456",
  "rol": "ADMIN"
}
```

### 2. Login ADMIN

```http
POST {{gateway_url}}/auth-service/auth/login
```

```json
{
  "username": "admin",
  "password": "123456"
}
```

Copiar el token en la variable `token_admin`.

### 3. Crear cliente

```http
POST {{gateway_url}}/cliente-service/clientes
```

```json
{
  "nombre": "Pedro",
  "apellido": "Gonzalez",
  "rut": "22222222-2",
  "correo": "pedro@gmail.com",
  "telefono": "987654321",
  "direccion": "Santiago Centro"
}
```

### 4. Crear vehículo

```http
POST {{gateway_url}}/vehiculo-service/vehiculos
```

```json
{
  "patente": "ABCD12",
  "marca": "Toyota",
  "modelo": "Yaris",
  "anio": 2020,
  "color": "Rojo",
  "clienteId": 1
}
```

### 5. Crear mecánico

```http
POST {{gateway_url}}/mecanico-service/mecanicos
```

```json
{
  "nombre": "Mario",
  "apellido": "Rojas",
  "rut": "33333333-3",
  "especialidad": "Motor",
  "telefono": "912345678",
  "correo": "mario@gmail.com"
}
```

### 6. Crear repuesto

```http
POST {{gateway_url}}/inventario-service/inventario
```

```json
{
  "codigo": "REP-001",
  "nombre": "Filtro de aceite",
  "descripcion": "Filtro Bosch",
  "precio": 7990,
  "stock": 20,
  "marca": "Bosch"
}
```

### 7. Crear orden de trabajo

```http
POST {{gateway_url}}/orden-trabajo-service/ordenes
```

```json
{
  "diagnostico": "Ruido en frenos",
  "descripcion": "Cambio de pastillas",
  "costoManoObra": 35000,
  "vehiculoId": 1,
  "mecanicoId": 1
}
```

### 8. Cambiar estado de orden

```http
PUT {{gateway_url}}/orden-trabajo-service/ordenes/1/estado?estado=FINALIZADO
```

### 9. Crear pago

```http
POST {{gateway_url}}/pago-service/pagos
```

```json
{
  "metodoPago": "EFECTIVO",
  "ordenTrabajoId": 1
}
```

### 10. Crear venta

```http
POST {{gateway_url}}/venta-service/ventas
```

```json
{
  "clienteId": 1,
  "repuestoId": 1,
  "cantidad": 2,
  "metodoPago": "EFECTIVO"
}
```

### 11. Registrar evento de seguridad

```http
POST {{gateway_url}}/security-service/security-events
```

```json
{
  "username": "admin",
  "accion": "LOGIN",
  "descripcion": "Acceso administrador",
  "ip": "127.0.0.1",
  "exitoso": true
}
```

## Pruebas de seguridad

### Sin token

```http
GET {{gateway_url}}/cliente-service/clientes
```

Resultado esperado: error 401 o 403.

### Con token ADMIN

```http
GET {{gateway_url}}/cliente-service/clientes
```

Resultado esperado: listado de clientes.

## Pruebas de validación

Enviar cliente inválido:

```json
{
  "nombre": "",
  "correo": "correo-malo"
}
```

Resultado esperado: error de validación con response estándar.
