package com.taller_mecanico.orden_trabajo_service.dto;

import lombok.Data;

@Data
public class MecanicoDTO {

    private Long id;

    private String nombre;

    private String apellido;

    private String rut;

    private String especialidad;

    private String telefono;

    private String correo;

    private Boolean activo;
}
