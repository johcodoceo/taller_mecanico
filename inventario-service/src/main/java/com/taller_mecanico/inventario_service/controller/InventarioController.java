package com.taller_mecanico.inventario_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taller_mecanico.inventario_service.dto.RepuestoRequest;
import com.taller_mecanico.inventario_service.entity.Repuesto;
import com.taller_mecanico.inventario_service.service.InventarioService;
import com.taller_mecanico.inventario_service.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Repuesto>> crearRepuesto(@Valid @RequestBody RepuestoRequest request) {
        Repuesto repuesto = inventarioService.crearRepuesto(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Repuesto creado correctamente", repuesto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Repuesto>>> listarRepuestos() {
        List<Repuesto> repuestos = inventarioService.listarRepuestos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Repuestos obtenidos correctamente", repuestos));
    }


    @PutMapping("/{id}/aumentar")
    public ResponseEntity<ApiResponse<Repuesto>> aumentarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        Repuesto repuesto = inventarioService.aumentarStock(id, cantidad);
        return ResponseEntity.ok(new ApiResponse<>(true, "Stock aumentado correctamente", repuesto));
    }

    @PutMapping("/{id}/descontar")
    public ResponseEntity<ApiResponse<Repuesto>> descontarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        Repuesto repuesto = inventarioService.descontarStock(id, cantidad);
        return ResponseEntity.ok(new ApiResponse<>(true, "Stock descontado correctamente", repuesto));
    }
}
