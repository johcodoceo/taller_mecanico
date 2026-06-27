package com.taller_mecanico.pago_service.dto;

import com.taller_mecanico.pago_service.enums.MetodoPago;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoRequest {

    @NotNull(message = "El método de pago es obligatorio")
    private MetodoPago metodoPago;

    @NotNull(message = "El ordenTrabajoId es obligatorio")
    private Long ordenTrabajoId;
}
