package com.taller_mecanico.venta_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taller_mecanico.venta_service.dto.VentaRequest;
import com.taller_mecanico.venta_service.entity.Venta;
import com.taller_mecanico.venta_service.service.VentaService;
import com.taller_mecanico.venta_service.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Venta>> crearVenta(@Valid @RequestBody VentaRequest request) {
        Venta venta = ventaService.crearVenta(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Venta creada correctamente", venta));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Venta>>> listar() {
        List<Venta> ventas = ventaService.listar();
        return ResponseEntity.ok(new ApiResponse<>(true, "Ventas obtenidas correctamente", ventas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Venta>> buscarPorId(@PathVariable Long id) {
        Venta venta = ventaService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Venta obtenida correctamente", venta));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ApiResponse<List<Venta>>> listarPorCliente(@PathVariable Long clienteId) {
        List<Venta> ventas = ventaService.listarPorCliente(clienteId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Ventas del cliente obtenidas correctamente", ventas));
    }

    @PutMapping("/{id}/anular")
    public ResponseEntity<ApiResponse<Venta>> anular(@PathVariable Long id) {
        Venta venta = ventaService.anular(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Venta anulada correctamente", venta));
    }
}
