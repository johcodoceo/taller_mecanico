package com.taller_mecanico.security_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "security_events")
@Data
public class SecurityEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;
    private String username;
    private String tipoEvento;
    private String descripcion;
    private String ipOrigen;
    private LocalDateTime fechaEvento;
    private Boolean exitoso;
}
