package com.taller_mecanico.orden_trabajo_service.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taller_mecanico.orden_trabajo_service.client.MecanicoFeignClient;
import com.taller_mecanico.orden_trabajo_service.client.VehiculoFeignClient;
import com.taller_mecanico.orden_trabajo_service.dto.MecanicoDTO;
import com.taller_mecanico.orden_trabajo_service.dto.OrdenTrabajoRequest;
import com.taller_mecanico.orden_trabajo_service.dto.VehiculoDTO;
import com.taller_mecanico.orden_trabajo_service.entity.OrdenTrabajo;
import com.taller_mecanico.orden_trabajo_service.enums.EstadoOrden;
import com.taller_mecanico.orden_trabajo_service.exception.ResourceNotFoundException;
import com.taller_mecanico.orden_trabajo_service.repository.OrdenTrabajoRepository;
import com.taller_mecanico.orden_trabajo_service.util.ApiResponse;

@Service
public class OrdenTrabajoService {

    private final OrdenTrabajoRepository ordenTrabajoRepository;
    private final VehiculoFeignClient vehiculoFeignClient;
    private final MecanicoFeignClient mecanicoFeignClient;

    public OrdenTrabajoService(OrdenTrabajoRepository ordenTrabajoRepository,
                               VehiculoFeignClient vehiculoFeignClient,
                               MecanicoFeignClient mecanicoFeignClient) {
        this.ordenTrabajoRepository = ordenTrabajoRepository;
        this.vehiculoFeignClient = vehiculoFeignClient;
        this.mecanicoFeignClient = mecanicoFeignClient;
    }

    public OrdenTrabajo crearOrden(OrdenTrabajoRequest request) {
        ApiResponse<VehiculoDTO> vehiculoResponse = vehiculoFeignClient.buscarVehiculo(request.getVehiculoId());
        ApiResponse<MecanicoDTO> mecanicoResponse = mecanicoFeignClient.buscarMecanico(request.getMecanicoId());

        if (vehiculoResponse == null || vehiculoResponse.getData() == null) {
            throw new ResourceNotFoundException("Vehículo no existe");
        }

        if (mecanicoResponse == null || mecanicoResponse.getData() == null) {
            throw new ResourceNotFoundException("Mecánico no existe");
        }

        MecanicoDTO mecanico = mecanicoResponse.getData();
        if (mecanico.getActivo() != null && !mecanico.getActivo()) {
            throw new IllegalArgumentException("No se puede asignar una orden a un mecánico inactivo");
        }

        OrdenTrabajo orden = new OrdenTrabajo();
        orden.setFechaIngreso(LocalDate.now());
        orden.setDiagnostico(request.getDiagnostico());
        orden.setDescripcion(request.getDescripcion());
        orden.setCostoManoObra(request.getCostoManoObra());
        orden.setVehiculoId(request.getVehiculoId());
        orden.setMecanicoId(request.getMecanicoId());
        orden.setEstado(EstadoOrden.PENDIENTE);

        return ordenTrabajoRepository.save(orden);
    }

    public List<OrdenTrabajo> listarOrdenes() {
        return ordenTrabajoRepository.findAll();
    }

    public OrdenTrabajo cambiarEstado(Long id, EstadoOrden estado) {
        OrdenTrabajo orden = ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada"));

        orden.setEstado(estado);

        if (estado == EstadoOrden.ENTREGADO) {
            orden.setFechaEntrega(LocalDate.now());
        }

        return ordenTrabajoRepository.save(orden);
    }

    public OrdenTrabajo buscarPorId(Long id) {
        return ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada"));
    }
}
