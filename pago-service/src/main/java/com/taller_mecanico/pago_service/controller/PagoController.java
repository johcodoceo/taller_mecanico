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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Pago>> crearPago(@Valid @RequestBody PagoRequest request) {
        Pago pago = pagoService.crearPago(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Pago creado correctamente", pago));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Pago>>> listarPagos() {
        List<Pago> pagos = pagoService.listarPagos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Pagos obtenidos correctamente", pagos));
    }
}
