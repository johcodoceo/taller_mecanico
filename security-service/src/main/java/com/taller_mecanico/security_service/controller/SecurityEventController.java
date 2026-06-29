package com.taller_mecanico.security_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taller_mecanico.security_service.dto.SecurityEventRequest;
import com.taller_mecanico.security_service.entity.SecurityEvent;
import com.taller_mecanico.security_service.service.SecurityEventService;
import com.taller_mecanico.security_service.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/security-events")
@Tag(name = "Eventos de seguridad", description = "Registro y consulta de eventos de seguridad del sistema")
public class SecurityEventController {

    private final SecurityEventService securityEventService;

    public SecurityEventController(SecurityEventService securityEventService) {
        this.securityEventService = securityEventService;
    }

    @Operation(
            summary = "Registrar evento de seguridad",
            description = "Guarda un evento de seguridad, como intentos de acceso, acciones relevantes o eventos fallidos."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Evento de seguridad registrado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos del evento inválidos")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<SecurityEvent>> registrar(@Valid @RequestBody SecurityEventRequest request) {
        SecurityEvent evento = securityEventService.registrar(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Evento de seguridad registrado correctamente", evento));
    }

    @Operation(
            summary = "Listar eventos de seguridad",
            description = "Obtiene todos los eventos de seguridad registrados en el sistema."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Eventos de seguridad obtenidos correctamente")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<SecurityEvent>>> listar() {
        List<SecurityEvent> eventos = securityEventService.listar();
        return ResponseEntity.ok(new ApiResponse<>(true, "Eventos de seguridad obtenidos correctamente", eventos));
    }

    @Operation(
            summary = "Buscar evento de seguridad por ID",
            description = "Obtiene el detalle de un evento de seguridad específico mediante su identificador."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Evento de seguridad obtenido correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Evento de seguridad no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SecurityEvent>> buscarPorId(@PathVariable Long id) {
        SecurityEvent evento = securityEventService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Evento de seguridad obtenido correctamente", evento));
    }

    @Operation(
            summary = "Listar eventos por usuario",
            description = "Obtiene los eventos de seguridad asociados a un nombre de usuario específico."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Eventos del usuario obtenidos correctamente")
    })
    @GetMapping("/usuario/{username}")
    public ResponseEntity<ApiResponse<List<SecurityEvent>>> listarPorUsername(@PathVariable String username) {
        List<SecurityEvent> eventos = securityEventService.listarPorUsername(username);
        return ResponseEntity.ok(new ApiResponse<>(true, "Eventos del usuario obtenidos correctamente", eventos));
    }

    @Operation(
            summary = "Listar eventos fallidos",
            description = "Obtiene los eventos de seguridad marcados como fallidos para tareas de auditoría y seguimiento."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Eventos fallidos obtenidos correctamente")
    })
    @GetMapping("/fallidos")
    public ResponseEntity<ApiResponse<List<SecurityEvent>>> listarFallidos() {
        List<SecurityEvent> eventos = securityEventService.listarFallidos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Eventos fallidos obtenidos correctamente", eventos));
    }
}
