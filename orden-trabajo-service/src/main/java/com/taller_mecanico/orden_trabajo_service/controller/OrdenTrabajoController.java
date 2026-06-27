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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ordenes")
public class OrdenTrabajoController {

    private final OrdenTrabajoService ordenTrabajoService;

    public OrdenTrabajoController(OrdenTrabajoService ordenTrabajoService) {
        this.ordenTrabajoService = ordenTrabajoService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrdenTrabajo>> crearOrden(@Valid @RequestBody OrdenTrabajoRequest request) {
        OrdenTrabajo orden = ordenTrabajoService.crearOrden(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Orden de trabajo creada correctamente", orden));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrdenTrabajo>>> listarOrdenes() {
        List<OrdenTrabajo> ordenes = ordenTrabajoService.listarOrdenes();
        return ResponseEntity.ok(new ApiResponse<>(true, "Órdenes de trabajo obtenidas correctamente", ordenes));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<OrdenTrabajo>> cambiarEstado(@PathVariable Long id, @RequestParam EstadoOrden estado) {
        OrdenTrabajo orden = ordenTrabajoService.cambiarEstado(id, estado);
        return ResponseEntity.ok(new ApiResponse<>(true, "Estado de orden actualizado correctamente", orden));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrdenTrabajo>> buscarOrden(@PathVariable Long id) {
        OrdenTrabajo orden = ordenTrabajoService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Orden de trabajo obtenida correctamente", orden));
    }
}
