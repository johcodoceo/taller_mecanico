# Checklist para defensa del proyecto

## Evidencias técnicas mínimas

- [ ] Todos los microservicios levantan sin errores.
- [ ] Todos aparecen como `UP` en Eureka.
- [ ] API Gateway enruta correctamente.
- [ ] Login genera JWT.
- [ ] Endpoints protegidos rechazan solicitudes sin token.
- [ ] Endpoints permiten acceso con token ADMIN.
- [ ] Swagger funciona en los microservicios de negocio.
- [ ] Response estándar visible en los endpoints.
- [ ] Validaciones devuelven errores controlados.
- [ ] Excepciones globales devuelven `ApiResponse`.
- [ ] Comunicación entre microservicios funciona.
- [ ] Se evidencia base de datos independiente por servicio.
- [ ] Colección Postman importada y probada.
- [ ] README actualizado.
- [ ] Diagramas disponibles.
- [ ] Pruebas unitarias básicas ejecutadas.
- [ ] Proyecto subido a GitHub con commits claros.

## Guion breve de defensa

1. Presentar el problema: gestión de taller mecánico y venta de repuestos.
2. Explicar por qué se justifica microservicios: múltiples módulos desacoplados.
3. Mostrar arquitectura: Eureka, Gateway, Auth y servicios de negocio.
4. Mostrar flujo: cliente, vehículo, mecánico, orden, pago, inventario y venta.
5. Mostrar seguridad: login, JWT y roles.
6. Mostrar Swagger y Postman.
7. Mostrar validaciones y errores controlados.
8. Mostrar pruebas y documentación.
9. Explicar mejoras futuras: Docker, frontend, Kafka o despliegue cloud.
