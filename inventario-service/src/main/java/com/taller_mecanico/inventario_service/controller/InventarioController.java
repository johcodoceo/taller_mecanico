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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventario")
@Tag(name = "Inventario", description = "Gestión de repuestos, existencias y movimientos de stock")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @Operation(
            summary = "Crear repuesto",
            description = "Registra un nuevo repuesto en el inventario del taller mecánico."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Repuesto creado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos del repuesto inválidos")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Repuesto>> crearRepuesto(@Valid @RequestBody RepuestoRequest request) {
        Repuesto repuesto = inventarioService.crearRepuesto(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Repuesto creado correctamente", repuesto));
    }

    @Operation(
            summary = "Listar repuestos",
            description = "Obtiene todos los repuestos registrados en el inventario."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Repuestos obtenidos correctamente")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<Repuesto>>> listarRepuestos() {
        List<Repuesto> repuestos = inventarioService.listarRepuestos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Repuestos obtenidos correctamente", repuestos));
    }

    @Operation(
            summary = "Aumentar stock",
            description = "Incrementa la cantidad disponible de un repuesto específico."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Stock aumentado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Cantidad inválida"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Repuesto no encontrado")
    })
    @PutMapping("/{id}/aumentar")
    public ResponseEntity<ApiResponse<Repuesto>> aumentarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        Repuesto repuesto = inventarioService.aumentarStock(id, cantidad);
        return ResponseEntity.ok(new ApiResponse<>(true, "Stock aumentado correctamente", repuesto));
    }

    @Operation(
            summary = "Descontar stock",
            description = "Disminuye la cantidad disponible de un repuesto específico cuando es utilizado o vendido."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Stock descontado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Cantidad inválida o stock insuficiente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Repuesto no encontrado")
    })
    @PutMapping("/{id}/descontar")
    public ResponseEntity<ApiResponse<Repuesto>> descontarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        Repuesto repuesto = inventarioService.descontarStock(id, cantidad);
        return ResponseEntity.ok(new ApiResponse<>(true, "Stock descontado correctamente", repuesto));
    }
}
