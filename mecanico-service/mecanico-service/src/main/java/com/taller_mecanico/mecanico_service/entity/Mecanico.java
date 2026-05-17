package com.taller_mecanico.mecanico_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mecanicos")
@Data
public class Mecanico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String rut;

    private String nombre;
    private String apellido;
    private String especialidad;
    private String telefono;
    private String correo;
    private Boolean activo = true;
}
