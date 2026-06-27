package com.taller_mecanico.venta_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VentaDetalleRequest {

    @NotNull(message = "El id del repuesto es obligatorio")
    private Long repuestoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a cero")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 1, message = "El precio unitario debe ser mayor a cero")
    private Double precioUnitario;
}
