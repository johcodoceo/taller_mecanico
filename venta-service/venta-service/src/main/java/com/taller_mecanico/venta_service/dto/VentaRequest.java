package com.taller_mecanico.venta_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class VentaRequest {

    @NotNull(message = "El id del cliente es obligatorio")
    private Long clienteId;

    @Valid
    @NotEmpty(message = "La venta debe incluir al menos un repuesto")
    private List<VentaDetalleRequest> detalles;
}
