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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Vehiculo>> crearVehiculo(@Valid @RequestBody VehiculoRequest request) {
        Vehiculo vehiculo = vehiculoService.crearVehiculo(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Vehículo creado correctamente", vehiculo));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Vehiculo>>> listarVehiculos() {
        List<Vehiculo> vehiculos = vehiculoService.listarVehiculos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Vehículos obtenidos correctamente", vehiculos));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ApiResponse<List<Vehiculo>>> buscarPorCliente(@PathVariable Long clienteId) {
        List<Vehiculo> vehiculos = vehiculoService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Vehículos del cliente obtenidos correctamente", vehiculos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Vehiculo>> buscarVehiculo(@PathVariable Long id) {
        Vehiculo vehiculo = vehiculoService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Vehículo obtenido correctamente", vehiculo));
    }
}
