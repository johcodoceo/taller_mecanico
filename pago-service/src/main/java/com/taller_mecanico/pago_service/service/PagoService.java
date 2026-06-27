package com.taller_mecanico.pago_service.service;

import java.time.LocalDate;
import java.util.List;

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

    private final PagoRepository pagoRepository;
    private final OrdenTrabajoFeignClient ordenTrabajoFeignClient;

    public PagoService(PagoRepository pagoRepository, OrdenTrabajoFeignClient ordenTrabajoFeignClient) {
        this.pagoRepository = pagoRepository;
        this.ordenTrabajoFeignClient = ordenTrabajoFeignClient;
    }

    public Pago crearPago(PagoRequest request) {
        ApiResponse<OrdenTrabajoDTO> response = ordenTrabajoFeignClient.buscarOrden(request.getOrdenTrabajoId());

        if (response == null || response.getData() == null) {
            throw new ResourceNotFoundException("Orden no existe");
        }

        OrdenTrabajoDTO orden = response.getData();

        Pago pago = new Pago();
        pago.setFechaPago(LocalDate.now());
        pago.setMetodoPago(request.getMetodoPago());
        pago.setEstado(EstadoPago.PAGADO);
        pago.setOrdenTrabajoId(request.getOrdenTrabajoId());
        pago.setTotal(orden.getCostoManoObra());

        return pagoRepository.save(pago);
    }

    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }
}
