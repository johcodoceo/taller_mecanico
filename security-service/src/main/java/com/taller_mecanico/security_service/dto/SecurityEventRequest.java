package com.taller_mecanico.security_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SecurityEventRequest {

    private Long usuarioId;

    @NotBlank(message = "El username es obligatorio")
    private String username;

    @NotBlank(message = "El tipo de evento es obligatorio")
    private String tipoEvento;

    private String descripcion;
    private String ipOrigen;

    @NotNull(message = "Debe indicar si el evento fue exitoso")
    private Boolean exitoso;
}
