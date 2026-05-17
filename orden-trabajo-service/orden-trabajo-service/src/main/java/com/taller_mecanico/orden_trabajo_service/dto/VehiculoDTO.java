package com.taller_mecanico.orden_trabajo_service.dto;

import lombok.Data;

@Data
public class VehiculoDTO {

    private Long id;

    private String patente;

    private String marca;

    private String modelo;
}
