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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ventas")
@Tag(name = "Ventas", description = "Gestión de ventas de repuestos o servicios del taller")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @Operation(
            summary = "Crear venta",
            description = "Registra una nueva venta asociada a un cliente y a los detalles comerciales correspondientes."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Venta creada correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de la venta inválidos")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Venta>> crearVenta(@Valid @RequestBody VentaRequest request) {
        Venta venta = ventaService.crearVenta(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Venta creada correctamente", venta));
    }

    @Operation(
            summary = "Listar ventas",
            description = "Obtiene todas las ventas registradas en el sistema."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ventas obtenidas correctamente")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<Venta>>> listar() {
        List<Venta> ventas = ventaService.listar();
        return ResponseEntity.ok(new ApiResponse<>(true, "Ventas obtenidas correctamente", ventas));
    }

    @Operation(
            summary = "Buscar venta por ID",
            description = "Obtiene el detalle de una venta específica mediante su identificador."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Venta obtenida correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Venta>> buscarPorId(@PathVariable Long id) {
        Venta venta = ventaService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Venta obtenida correctamente", venta));
    }

    @Operation(
            summary = "Listar ventas por cliente",
            description = "Obtiene todas las ventas asociadas a un cliente específico."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ventas del cliente obtenidas correctamente")
    })
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ApiResponse<List<Venta>>> listarPorCliente(@PathVariable Long clienteId) {
        List<Venta> ventas = ventaService.listarPorCliente(clienteId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Ventas del cliente obtenidas correctamente", ventas));
    }

    @Operation(
            summary = "Anular venta",
            description = "Cambia el estado de una venta para dejarla anulada sin eliminar el registro histórico."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Venta anulada correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @PutMapping("/{id}/anular")
    public ResponseEntity<ApiResponse<Venta>> anular(@PathVariable Long id) {
        Venta venta = ventaService.anular(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Venta anulada correctamente", venta));
    }
}
