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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Cliente>> crearCliente(@Valid @RequestBody ClienteRequest request) {
        Cliente cliente = clienteService.crearCliente(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente creado correctamente", cliente));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cliente>>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(new ApiResponse<>(true, "Clientes obtenidos correctamente", clientes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Cliente>> buscarCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente obtenido correctamente", cliente));
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<ApiResponse<Cliente>> buscarPorRut(@PathVariable String rut) {
        Cliente cliente = clienteService.buscarPorRut(rut);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente obtenido correctamente", cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Cliente>> actualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        Cliente cliente = clienteService.actualizarCliente(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente actualizado correctamente", cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente eliminado correctamente", null));
    }
}
