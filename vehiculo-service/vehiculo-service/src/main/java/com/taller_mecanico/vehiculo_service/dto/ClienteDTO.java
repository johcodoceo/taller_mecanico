package com.taller_mecanico.vehiculo_service.dto;

import lombok.Data;

@Data
public class ClienteDTO {

    private Long id;

    private String nombre;

    private String apellido;

    private String rut;

    private String correo;
}
