package com.taller_mecanico.orden_trabajo_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class OrdenTrabajoRequest {

    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnostico;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El costo de mano de obra es obligatorio")
    @PositiveOrZero(message = "El costo de mano de obra no puede ser negativo")
    private Double costoManoObra;

    @NotNull(message = "El vehiculoId es obligatorio")
    private Long vehiculoId;

    @NotNull(message = "El mecanicoId es obligatorio")
    private Long mecanicoId;
}
