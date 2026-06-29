package com.taller_mecanico.orden_trabajo_service.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(OrdenTrabajoService.class);

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
        logger.info("event=orden_create_start vehiculoId={} mecanicoId={} costoManoObra={}",
                request.getVehiculoId(), request.getMecanicoId(), request.getCostoManoObra());

        ApiResponse<VehiculoDTO> vehiculoResponse = vehiculoFeignClient.buscarVehiculo(request.getVehiculoId());
        ApiResponse<MecanicoDTO> mecanicoResponse = mecanicoFeignClient.buscarMecanico(request.getMecanicoId());

        if (vehiculoResponse == null || vehiculoResponse.getData() == null) {
            logger.warn("event=orden_create_rejected reason=vehiculo_not_found vehiculoId={}", request.getVehiculoId());
            throw new ResourceNotFoundException("Vehículo no existe");
        }

        if (mecanicoResponse == null || mecanicoResponse.getData() == null) {
            logger.warn("event=orden_create_rejected reason=mecanico_not_found mecanicoId={}", request.getMecanicoId());
            throw new ResourceNotFoundException("Mecánico no existe");
        }

        MecanicoDTO mecanico = mecanicoResponse.getData();
        if (mecanico.getActivo() != null && !mecanico.getActivo()) {
            logger.warn("event=orden_create_rejected reason=mecanico_inactive mecanicoId={}", request.getMecanicoId());
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

        OrdenTrabajo ordenGuardada = ordenTrabajoRepository.save(orden);
        logger.info("event=orden_create_success ordenId={} vehiculoId={} mecanicoId={} estado={}",
                ordenGuardada.getId(), ordenGuardada.getVehiculoId(), ordenGuardada.getMecanicoId(), ordenGuardada.getEstado());

        return ordenGuardada;
    }

    public List<OrdenTrabajo> listarOrdenes() {
        logger.info("event=orden_list_start");
        List<OrdenTrabajo> ordenes = ordenTrabajoRepository.findAll();
        logger.info("event=orden_list_success total={}", ordenes.size());
        return ordenes;
    }

    public OrdenTrabajo cambiarEstado(Long id, EstadoOrden estado) {
        logger.info("event=orden_status_change_start ordenId={} nuevoEstado={}", id, estado);

        OrdenTrabajo orden = ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("event=orden_status_change_not_found ordenId={} nuevoEstado={}", id, estado);
                    return new ResourceNotFoundException("Orden no encontrada");
                });

        EstadoOrden estadoAnterior = orden.getEstado();
        orden.setEstado(estado);

        if (estado == EstadoOrden.ENTREGADO) {
            orden.setFechaEntrega(LocalDate.now());
        }

        OrdenTrabajo ordenActualizada = ordenTrabajoRepository.save(orden);
        logger.info("event=orden_status_change_success ordenId={} estadoAnterior={} estadoActual={}",
                ordenActualizada.getId(), estadoAnterior, ordenActualizada.getEstado());

        return ordenActualizada;
    }

    public OrdenTrabajo buscarPorId(Long id) {
        logger.info("event=orden_find_by_id_start ordenId={}", id);
        return ordenTrabajoRepository.findById(id)
                .map(orden -> {
                    logger.info("event=orden_find_by_id_success ordenId={} estado={} vehiculoId={} mecanicoId={}",
                            orden.getId(), orden.getEstado(), orden.getVehiculoId(), orden.getMecanicoId());
                    return orden;
                })
                .orElseThrow(() -> {
                    logger.warn("event=orden_find_by_id_not_found ordenId={}", id);
                    return new ResourceNotFoundException("Orden no encontrada");
                });
    }
}
