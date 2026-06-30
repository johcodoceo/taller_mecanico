package com.taller_mecanico.pago_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taller_mecanico.pago_service.dto.PagoRequest;
import com.taller_mecanico.pago_service.entity.Pago;
import com.taller_mecanico.pago_service.service.PagoService;
import com.taller_mecanico.pago_service.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pagos")
@Tag(name = "Pagos", description = "Gestión de pagos asociados a servicios, órdenes de trabajo o ventas")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Operation(
            summary = "Crear pago",
            description = "Registra un pago realizado por un cliente, asociado al proceso comercial del taller."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Pago creado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos del pago inválidos")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Pago>> crearPago(@Valid @RequestBody PagoRequest request) {
        Pago pago = pagoService.crearPago(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Pago creado correctamente", pago));
    }

    @Operation(
            summary = "Listar pagos",
            description = "Obtiene todos los pagos registrados en el sistema."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Pagos obtenidos correctamente")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<Pago>>> listarPagos() {
        List<Pago> pagos = pagoService.listarPagos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Pagos obtenidos correctamente", pagos));
    }
}
