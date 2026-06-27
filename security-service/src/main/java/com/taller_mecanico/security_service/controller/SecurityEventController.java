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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/security-events")
public class SecurityEventController {

    private final SecurityEventService securityEventService;

    public SecurityEventController(SecurityEventService securityEventService) {
        this.securityEventService = securityEventService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SecurityEvent>> registrar(@Valid @RequestBody SecurityEventRequest request) {
        SecurityEvent evento = securityEventService.registrar(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Evento de seguridad registrado correctamente", evento));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SecurityEvent>>> listar() {
        List<SecurityEvent> eventos = securityEventService.listar();
        return ResponseEntity.ok(new ApiResponse<>(true, "Eventos de seguridad obtenidos correctamente", eventos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SecurityEvent>> buscarPorId(@PathVariable Long id) {
        SecurityEvent evento = securityEventService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Evento de seguridad obtenido correctamente", evento));
    }

    @GetMapping("/usuario/{username}")
    public ResponseEntity<ApiResponse<List<SecurityEvent>>> listarPorUsername(@PathVariable String username) {
        List<SecurityEvent> eventos = securityEventService.listarPorUsername(username);
        return ResponseEntity.ok(new ApiResponse<>(true, "Eventos del usuario obtenidos correctamente", eventos));
    }

    @GetMapping("/fallidos")
    public ResponseEntity<ApiResponse<List<SecurityEvent>>> listarFallidos() {
        List<SecurityEvent> eventos = securityEventService.listarFallidos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Eventos fallidos obtenidos correctamente", eventos));
    }
}
