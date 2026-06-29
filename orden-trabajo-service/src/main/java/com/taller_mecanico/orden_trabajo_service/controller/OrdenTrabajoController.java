package com.taller_mecanico.orden_trabajo_service.controller;

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

import com.taller_mecanico.orden_trabajo_service.dto.OrdenTrabajoRequest;
import com.taller_mecanico.orden_trabajo_service.entity.OrdenTrabajo;
import com.taller_mecanico.orden_trabajo_service.enums.EstadoOrden;
import com.taller_mecanico.orden_trabajo_service.service.OrdenTrabajoService;
import com.taller_mecanico.orden_trabajo_service.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ordenes")
@Tag(name = "Órdenes de trabajo", description = "Gestión de órdenes de trabajo, diagnóstico, reparación y estados")
public class OrdenTrabajoController {

    private final OrdenTrabajoService ordenTrabajoService;

    public OrdenTrabajoController(OrdenTrabajoService ordenTrabajoService) {
        this.ordenTrabajoService = ordenTrabajoService;
    }

    @Operation(
            summary = "Crear orden de trabajo",
            description = "Registra una orden de trabajo asociada a cliente, vehículo, mecánico y detalle del servicio solicitado."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Orden de trabajo creada correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de la orden inválidos")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<OrdenTrabajo>> crearOrden(@Valid @RequestBody OrdenTrabajoRequest request) {
        OrdenTrabajo orden = ordenTrabajoService.crearOrden(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Orden de trabajo creada correctamente", orden));
    }

    @Operation(
            summary = "Listar órdenes de trabajo",
            description = "Obtiene todas las órdenes de trabajo registradas en el sistema."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Órdenes de trabajo obtenidas correctamente")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrdenTrabajo>>> listarOrdenes() {
        List<OrdenTrabajo> ordenes = ordenTrabajoService.listarOrdenes();
        return ResponseEntity.ok(new ApiResponse<>(true, "Órdenes de trabajo obtenidas correctamente", ordenes));
    }

    @Operation(
            summary = "Cambiar estado de orden",
            description = "Actualiza el estado de una orden de trabajo, por ejemplo pendiente, en proceso, finalizada o cancelada."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Estado de orden actualizado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Estado inválido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Orden de trabajo no encontrada")
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<OrdenTrabajo>> cambiarEstado(@PathVariable Long id, @RequestParam EstadoOrden estado) {
        OrdenTrabajo orden = ordenTrabajoService.cambiarEstado(id, estado);
        return ResponseEntity.ok(new ApiResponse<>(true, "Estado de orden actualizado correctamente", orden));
    }

    @Operation(
            summary = "Buscar orden de trabajo por ID",
            description = "Obtiene el detalle de una orden de trabajo específica mediante su identificador."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Orden de trabajo obtenida correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Orden de trabajo no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrdenTrabajo>> buscarOrden(@PathVariable Long id) {
        OrdenTrabajo orden = ordenTrabajoService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Orden de trabajo obtenida correctamente", orden));
    }
}
