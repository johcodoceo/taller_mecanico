# Mockito agregado al proyecto Taller Mecánico

## Qué se agregó

Se centralizaron las dependencias de pruebas en el `pom.xml` padre:

- `spring-boot-starter-test`
- `mockito-core`
- `mockito-junit-jupiter`

De esta forma, los microservicios heredan las dependencias de prueba desde el proyecto padre y no es necesario repetirlas en cada `pom.xml` de cada microservicio.

## Archivos principales modificados

- `pom.xml` padre: contiene las dependencias comunes de testing/Mockito.
- POM de cada microservicio: se eliminó la repetición de `spring-boot-starter-test`.
- Pruebas unitarias agregadas con Mockito en:
  - `auth-service`
  - `cliente-service`
  - `user-service`
  - `vehiculo-service`
  - `mecanico-service`
  - `inventario-service`
  - `orden-trabajo-service`
  - `pago-service`
  - `venta-service`
  - `security-service`

## Cómo ejecutar las pruebas

Desde la carpeta raíz del proyecto:

```bash
mvn test
```

Para ejecutar las pruebas de un solo microservicio:

```bash
mvn -pl cliente-service test
```

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

Con `@Mock` se simula una dependencia, por ejemplo un repositorio. Con `@InjectMocks` se crea el servicio real y se inyectan los mocks.
