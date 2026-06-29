package com.taller_mecanico.cliente_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taller_mecanico.cliente_service.dto.ClienteRequest;
import com.taller_mecanico.cliente_service.entity.Cliente;
import com.taller_mecanico.cliente_service.service.ClienteService;
import com.taller_mecanico.cliente_service.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Gestión de clientes registrados en el taller mecánico")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(
            summary = "Crear cliente",
            description = "Registra un nuevo cliente con sus datos personales y de contacto."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cliente creado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos del cliente inválidos")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Cliente>> crearCliente(@Valid @RequestBody ClienteRequest request) {
        Cliente cliente = clienteService.crearCliente(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente creado correctamente", cliente));
    }

    @Operation(
            summary = "Listar clientes",
            description = "Obtiene el listado completo de clientes registrados en el sistema."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Clientes obtenidos correctamente")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<Cliente>>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(new ApiResponse<>(true, "Clientes obtenidos correctamente", clientes));
    }

    @Operation(
            summary = "Buscar cliente por ID",
            description = "Obtiene la información de un cliente específico usando su identificador interno."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cliente obtenido correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cliente>> buscarCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente obtenido correctamente", cliente));
    }

    @Operation(
            summary = "Buscar cliente por RUT",
            description = "Obtiene la información de un cliente específico usando su RUT."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cliente obtenido correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/rut/{rut}")
    public ResponseEntity<ApiResponse<Cliente>> buscarPorRut(@PathVariable String rut) {
        Cliente cliente = clienteService.buscarPorRut(rut);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente obtenido correctamente", cliente));
    }

    @Operation(
            summary = "Actualizar cliente",
            description = "Modifica los datos registrados de un cliente existente."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos del cliente inválidos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Cliente>> actualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        Cliente cliente = clienteService.actualizarCliente(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente actualizado correctamente", cliente));
    }

    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina un cliente del sistema según su identificador interno."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente eliminado correctamente", null));
    }
}
