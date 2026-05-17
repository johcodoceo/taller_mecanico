package com.taller_mecanico.mecanico_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taller_mecanico.mecanico_service.dto.MecanicoRequest;
import com.taller_mecanico.mecanico_service.entity.Mecanico;
import com.taller_mecanico.mecanico_service.service.MecanicoService;
import com.taller_mecanico.mecanico_service.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/mecanicos")
public class MecanicoController {

    private final MecanicoService mecanicoService;

    public MecanicoController(MecanicoService mecanicoService) {
        this.mecanicoService = mecanicoService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Mecanico>> crear(@Valid @RequestBody MecanicoRequest request) {
        Mecanico mecanico = mecanicoService.crear(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mecánico creado correctamente", mecanico));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Mecanico>>> listar() {
        List<Mecanico> mecanicos = mecanicoService.listar();
        return ResponseEntity.ok(new ApiResponse<>(true, "Mecánicos obtenidos correctamente", mecanicos));
    }

    @GetMapping("/activos")
    public ResponseEntity<ApiResponse<List<Mecanico>>> listarActivos() {
        List<Mecanico> mecanicos = mecanicoService.listarActivos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Mecánicos activos obtenidos correctamente", mecanicos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Mecanico>> buscarPorId(@PathVariable Long id) {
        Mecanico mecanico = mecanicoService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mecánico obtenido correctamente", mecanico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Mecanico>> actualizar(@PathVariable Long id, @Valid @RequestBody MecanicoRequest request) {
        Mecanico mecanico = mecanicoService.actualizar(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mecánico actualizado correctamente", mecanico));
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<ApiResponse<Mecanico>> desactivar(@PathVariable Long id) {
        Mecanico mecanico = mecanicoService.desactivar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mecánico desactivado correctamente", mecanico));
    }

    @PutMapping("/{id}/reactivar")
    public ResponseEntity<ApiResponse<Mecanico>> reactivar(@PathVariable Long id) {
        Mecanico mecanico = mecanicoService.reactivar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mecánico reactivado correctamente", mecanico));
    }
}