package com.taller_mecanico.pago_service.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taller_mecanico.pago_service.client.OrdenTrabajoFeignClient;
import com.taller_mecanico.pago_service.dto.OrdenTrabajoDTO;
import com.taller_mecanico.pago_service.dto.PagoRequest;
import com.taller_mecanico.pago_service.entity.Pago;
import com.taller_mecanico.pago_service.enums.EstadoPago;
import com.taller_mecanico.pago_service.exception.ResourceNotFoundException;
import com.taller_mecanico.pago_service.repository.PagoRepository;
import com.taller_mecanico.pago_service.util.ApiResponse;

@Service
public class PagoService {

    private static final Logger logger = LoggerFactory.getLogger(PagoService.class);

    private final PagoRepository pagoRepository;
    private final OrdenTrabajoFeignClient ordenTrabajoFeignClient;

    public PagoService(PagoRepository pagoRepository, OrdenTrabajoFeignClient ordenTrabajoFeignClient) {
        this.pagoRepository = pagoRepository;
        this.ordenTrabajoFeignClient = ordenTrabajoFeignClient;
    }

    public Pago crearPago(PagoRequest request) {
        logger.info("event=pago_create_start ordenTrabajoId={} metodoPago={}",
                request.getOrdenTrabajoId(), request.getMetodoPago());

        ApiResponse<OrdenTrabajoDTO> response = ordenTrabajoFeignClient.buscarOrden(request.getOrdenTrabajoId());

        if (response == null || response.getData() == null) {
            logger.warn("event=pago_create_rejected reason=orden_not_found ordenTrabajoId={}", request.getOrdenTrabajoId());
            throw new ResourceNotFoundException("Orden no existe");
        }

        OrdenTrabajoDTO orden = response.getData();

        Pago pago = new Pago();
        pago.setFechaPago(LocalDate.now());
        pago.setMetodoPago(request.getMetodoPago());
        pago.setEstado(EstadoPago.PAGADO);
        pago.setOrdenTrabajoId(request.getOrdenTrabajoId());
        pago.setTotal(orden.getCostoManoObra());

        Pago pagoGuardado = pagoRepository.save(pago);
        logger.info("event=pago_create_success pagoId={} ordenTrabajoId={} total={} estado={}",
                pagoGuardado.getId(), pagoGuardado.getOrdenTrabajoId(), pagoGuardado.getTotal(), pagoGuardado.getEstado());

        return pagoGuardado;
    }

    public List<Pago> listarPagos() {
        logger.info("event=pago_list_start");
        List<Pago> pagos = pagoRepository.findAll();
        logger.info("event=pago_list_success total={}", pagos.size());
        return pagos;
    }
}
