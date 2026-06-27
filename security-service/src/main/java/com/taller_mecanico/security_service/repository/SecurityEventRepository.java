package com.taller_mecanico.security_service.repository;

import com.taller_mecanico.security_service.entity.SecurityEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityEventRepository extends JpaRepository<SecurityEvent, Long> {
    List<SecurityEvent> findByUsername(String username);
    List<SecurityEvent> findByTipoEvento(String tipoEvento);
    List<SecurityEvent> findByExitosoFalse();
}
