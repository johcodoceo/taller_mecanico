package com.taller_mecanico.security_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taller_mecanico.security_service.dto.SecurityEventRequest;
import com.taller_mecanico.security_service.entity.SecurityEvent;
import com.taller_mecanico.security_service.exception.ResourceNotFoundException;
import com.taller_mecanico.security_service.repository.SecurityEventRepository;

@Service
public class SecurityEventService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityEventService.class);

    private final SecurityEventRepository securityEventRepository;

    public SecurityEventService(SecurityEventRepository securityEventRepository) {
        this.securityEventRepository = securityEventRepository;
    }

    public SecurityEvent registrar(SecurityEventRequest request) {
        logger.info("event=security_event_register_start usuarioId={} username={} tipoEvento={} exitoso={} ipOrigen={}",
                request.getUsuarioId(), request.getUsername(), request.getTipoEvento(), request.getExitoso(), request.getIpOrigen());

        SecurityEvent event = new SecurityEvent();
        event.setUsuarioId(request.getUsuarioId());
        event.setUsername(request.getUsername());
        event.setTipoEvento(request.getTipoEvento());
        event.setDescripcion(request.getDescripcion());
        event.setIpOrigen(request.getIpOrigen());
        event.setExitoso(request.getExitoso());
        event.setFechaEvento(LocalDateTime.now());

        SecurityEvent eventoGuardado = securityEventRepository.save(event);
        logger.info("event=security_event_register_success eventoId={} username={} tipoEvento={} exitoso={}",
                eventoGuardado.getId(), eventoGuardado.getUsername(), eventoGuardado.getTipoEvento(), eventoGuardado.getExitoso());

        return eventoGuardado;
    }

    public List<SecurityEvent> listar() {
        logger.info("event=security_event_list_start");
        List<SecurityEvent> eventos = securityEventRepository.findAll();
        logger.info("event=security_event_list_success total={}", eventos.size());
        return eventos;
    }

    public SecurityEvent buscarPorId(Long id) {
        logger.info("event=security_event_find_by_id_start eventoId={}", id);
        return securityEventRepository.findById(id)
                .map(evento -> {
                    logger.info("event=security_event_find_by_id_success eventoId={} username={} tipoEvento={}",
                            evento.getId(), evento.getUsername(), evento.getTipoEvento());
                    return evento;
                })
                .orElseThrow(() -> {
                    logger.warn("event=security_event_find_by_id_not_found eventoId={}", id);
                    return new ResourceNotFoundException("Evento de seguridad no encontrado con id: " + id);
                });
    }

    public List<SecurityEvent> listarPorUsername(String username) {
        logger.info("event=security_event_list_by_username_start username={}", username);
        List<SecurityEvent> eventos = securityEventRepository.findByUsername(username);
        logger.info("event=security_event_list_by_username_success username={} total={}", username, eventos.size());
        return eventos;
    }

    public List<SecurityEvent> listarFallidos() {
        logger.info("event=security_event_list_failed_start");
        List<SecurityEvent> eventos = securityEventRepository.findByExitosoFalse();
        logger.info("event=security_event_list_failed_success total={}", eventos.size());
        return eventos;
    }
}
