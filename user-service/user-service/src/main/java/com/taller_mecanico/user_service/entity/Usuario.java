package com.taller_mecanico.user_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String correo;

    private String telefono;

    private String direccion;

    @Column(unique = true)
    private String rut;

    private boolean activo = true;

    // Relación lógica con auth-service
    private Long authId;
}
