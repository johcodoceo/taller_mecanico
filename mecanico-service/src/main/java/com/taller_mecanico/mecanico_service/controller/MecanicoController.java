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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/mecanicos")
@Tag(name = "Mecánicos", description = "Gestión de mecánicos, disponibilidad y estado operativo")
public class MecanicoController {

    private final MecanicoService mecanicoService;

    public MecanicoController(MecanicoService mecanicoService) {
        this.mecanicoService = mecanicoService;
    }

    @Operation(
            summary = "Crear mecánico",
            description = "Registra un nuevo mecánico con su información personal y especialidad."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Mecánico creado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos del mecánico inválidos")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Mecanico>> crear(@Valid @RequestBody MecanicoRequest request) {
        Mecanico mecanico = mecanicoService.crear(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mecánico creado correctamente", mecanico));
    }

    @Operation(
            summary = "Listar mecánicos",
            description = "Obtiene el listado completo de mecánicos registrados."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Mecánicos obtenidos correctamente")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<Mecanico>>> listar() {
        List<Mecanico> mecanicos = mecanicoService.listar();
        return ResponseEntity.ok(new ApiResponse<>(true, "Mecánicos obtenidos correctamente", mecanicos));
    }

    @Operation(
            summary = "Listar mecánicos activos",
            description = "Obtiene únicamente los mecánicos que se encuentran activos y disponibles para asignación."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Mecánicos activos obtenidos correctamente")
    })
    @GetMapping("/activos")
    public ResponseEntity<ApiResponse<List<Mecanico>>> listarActivos() {
        List<Mecanico> mecanicos = mecanicoService.listarActivos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Mecánicos activos obtenidos correctamente", mecanicos));
    }

    @Operation(
            summary = "Buscar mecánico por ID",
            description = "Obtiene la información de un mecánico específico mediante su identificador."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Mecánico obtenido correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Mecánico no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Mecanico>> buscarPorId(@PathVariable Long id) {
        Mecanico mecanico = mecanicoService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mecánico obtenido correctamente", mecanico));
    }

    @Operation(
            summary = "Actualizar mecánico",
            description = "Actualiza los datos personales, especialidad o información asociada a un mecánico existente."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Mecánico actualizado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos del mecánico inválidos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Mecánico no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Mecanico>> actualizar(@PathVariable Long id, @Valid @RequestBody MecanicoRequest request) {
        Mecanico mecanico = mecanicoService.actualizar(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mecánico actualizado correctamente", mecanico));
    }

    @Operation(
            summary = "Desactivar mecánico",
            description = "Marca un mecánico como inactivo para impedir su asignación a nuevas órdenes de trabajo."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Mecánico desactivado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Mecánico no encontrado")
    })
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<ApiResponse<Mecanico>> desactivar(@PathVariable Long id) {
        Mecanico mecanico = mecanicoService.desactivar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mecánico desactivado correctamente", mecanico));
    }

    @Operation(
            summary = "Reactivar mecánico",
            description = "Marca un mecánico inactivo como activo para permitir nuevamente su asignación."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Mecánico reactivado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Mecánico no encontrado")
    })
    @PutMapping("/{id}/reactivar")
    public ResponseEntity<ApiResponse<Mecanico>> reactivar(@PathVariable Long id) {
        Mecanico mecanico = mecanicoService.reactivar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mecánico reactivado correctamente", mecanico));
    }
}
