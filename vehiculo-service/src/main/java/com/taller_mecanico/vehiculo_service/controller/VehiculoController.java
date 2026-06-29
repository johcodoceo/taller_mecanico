package com.taller_mecanico.vehiculo_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taller_mecanico.vehiculo_service.dto.VehiculoRequest;
import com.taller_mecanico.vehiculo_service.entity.Vehiculo;
import com.taller_mecanico.vehiculo_service.service.VehiculoService;
import com.taller_mecanico.vehiculo_service.util.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vehiculos")
@Tag(name = "Vehículos", description = "Gestión de vehículos vinculados a los clientes del taller")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @Operation(
            summary = "Crear vehículo",
            description = "Registra un vehículo y lo asocia a un cliente existente."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Vehículo creado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos del vehículo inválidos")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Vehiculo>> crearVehiculo(@Valid @RequestBody VehiculoRequest request) {
        Vehiculo vehiculo = vehiculoService.crearVehiculo(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Vehículo creado correctamente", vehiculo));
    }

    @Operation(
            summary = "Listar vehículos",
            description = "Obtiene el listado completo de vehículos registrados en el sistema."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Vehículos obtenidos correctamente")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<Vehiculo>>> listarVehiculos() {
        List<Vehiculo> vehiculos = vehiculoService.listarVehiculos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Vehículos obtenidos correctamente", vehiculos));
    }

    @Operation(
            summary = "Buscar vehículos por cliente",
            description = "Obtiene todos los vehículos asociados a un cliente específico."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Vehículos del cliente obtenidos correctamente")
    })
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ApiResponse<List<Vehiculo>>> buscarPorCliente(@PathVariable Long clienteId) {
        List<Vehiculo> vehiculos = vehiculoService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Vehículos del cliente obtenidos correctamente", vehiculos));
    }

    @Operation(
            summary = "Buscar vehículo por ID",
            description = "Obtiene la información de un vehículo específico mediante su identificador."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Vehículo obtenido correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Vehiculo>> buscarVehiculo(@PathVariable Long id) {
        Vehiculo vehiculo = vehiculoService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Vehículo obtenido correctamente", vehiculo));
    }
}
