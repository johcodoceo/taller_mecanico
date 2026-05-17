package com.taller_mecanico.security_service.service;

import com.taller_mecanico.security_service.dto.SecurityEventRequest;
import com.taller_mecanico.security_service.entity.SecurityEvent;
import com.taller_mecanico.security_service.exception.ResourceNotFoundException;
import com.taller_mecanico.security_service.repository.SecurityEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SecurityEventService {

    private final SecurityEventRepository securityEventRepository;

    public SecurityEventService(SecurityEventRepository securityEventRepository) {
        this.securityEventRepository = securityEventRepository;
    }

    public SecurityEvent registrar(SecurityEventRequest request) {
        SecurityEvent event = new SecurityEvent();
        event.setUsuarioId(request.getUsuarioId());
        event.setUsername(request.getUsername());
        event.setTipoEvento(request.getTipoEvento());
        event.setDescripcion(request.getDescripcion());
        event.setIpOrigen(request.getIpOrigen());
        event.setExitoso(request.getExitoso());
        event.setFechaEvento(LocalDateTime.now());
        return securityEventRepository.save(event);
    }

    public List<SecurityEvent> listar() {
        return securityEventRepository.findAll();
    }

    public SecurityEvent buscarPorId(Long id) {
        return securityEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento de seguridad no encontrado con id: " + id));
    }

    public List<SecurityEvent> listarPorUsername(String username) {
        return securityEventRepository.findByUsername(username);
    }

    public List<SecurityEvent> listarFallidos() {
        return securityEventRepository.findByExitosoFalse();
    }
}
