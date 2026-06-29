package com.taller_mecanico.venta_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taller_mecanico.venta_service.client.InventarioFeignClient;
import com.taller_mecanico.venta_service.dto.VentaDetalleRequest;
import com.taller_mecanico.venta_service.dto.VentaRequest;
import com.taller_mecanico.venta_service.entity.Venta;
import com.taller_mecanico.venta_service.entity.VentaDetalle;
import com.taller_mecanico.venta_service.exception.ResourceNotFoundException;
import com.taller_mecanico.venta_service.repository.VentaRepository;

@Service
public class VentaService {

    private static final Logger logger = LoggerFactory.getLogger(VentaService.class);

    private final VentaRepository ventaRepository;
    private final InventarioFeignClient inventarioFeignClient;

    public VentaService(VentaRepository ventaRepository, InventarioFeignClient inventarioFeignClient) {
        this.ventaRepository = ventaRepository;
        this.inventarioFeignClient = inventarioFeignClient;
    }

    public Venta crearVenta(VentaRequest request) {
        int totalDetalles = request.getDetalles() == null ? 0 : request.getDetalles().size();
        logger.info("event=venta_create_start clienteId={} totalDetalles={}", request.getClienteId(), totalDetalles);

        Venta venta = new Venta();
        venta.setClienteId(request.getClienteId());
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEstado("REGISTRADA");

        double total = 0.0;

        for (VentaDetalleRequest detalleRequest : request.getDetalles()) {
            logger.info("event=venta_stock_discount_start clienteId={} repuestoId={} cantidad={}",
                    request.getClienteId(), detalleRequest.getRepuestoId(), detalleRequest.getCantidad());

            inventarioFeignClient.descontarStock(detalleRequest.getRepuestoId(), detalleRequest.getCantidad());

            logger.info("event=venta_stock_discount_success clienteId={} repuestoId={} cantidad={}",
                    request.getClienteId(), detalleRequest.getRepuestoId(), detalleRequest.getCantidad());

            VentaDetalle detalle = new VentaDetalle();
            detalle.setRepuestoId(detalleRequest.getRepuestoId());
            detalle.setCantidad(detalleRequest.getCantidad());
            detalle.setPrecioUnitario(detalleRequest.getPrecioUnitario());
            detalle.setSubtotal(detalleRequest.getCantidad() * detalleRequest.getPrecioUnitario());
            detalle.setVenta(venta);

            venta.getDetalles().add(detalle);
            total += detalle.getSubtotal();
        }

        venta.setTotal(total);
        Venta ventaGuardada = ventaRepository.save(venta);
        logger.info("event=venta_create_success ventaId={} clienteId={} total={} estado={}",
                ventaGuardada.getId(), ventaGuardada.getClienteId(), ventaGuardada.getTotal(), ventaGuardada.getEstado());

        return ventaGuardada;
    }

    public List<Venta> listar() {
        logger.info("event=venta_list_start");
        List<Venta> ventas = ventaRepository.findAll();
        logger.info("event=venta_list_success total={}", ventas.size());
        return ventas;
    }

    public Venta buscarPorId(Long id) {
        logger.info("event=venta_find_by_id_start ventaId={}", id);
        return ventaRepository.findById(id)
                .map(venta -> {
                    logger.info("event=venta_find_by_id_success ventaId={} clienteId={} estado={}",
                            venta.getId(), venta.getClienteId(), venta.getEstado());
                    return venta;
                })
                .orElseThrow(() -> {
                    logger.warn("event=venta_find_by_id_not_found ventaId={}", id);
                    return new ResourceNotFoundException("Venta no encontrada con id: " + id);
                });
    }

    public List<Venta> listarPorCliente(Long clienteId) {
        logger.info("event=venta_list_by_cliente_start clienteId={}", clienteId);
        List<Venta> ventas = ventaRepository.findByClienteId(clienteId);
        logger.info("event=venta_list_by_cliente_success clienteId={} total={}", clienteId, ventas.size());
        return ventas;
    }

    public Venta anular(Long id) {
        logger.info("event=venta_cancel_start ventaId={}", id);
        Venta venta = buscarPorId(id);
        String estadoAnterior = venta.getEstado();
        venta.setEstado("ANULADA");
        Venta ventaAnulada = ventaRepository.save(venta);
        logger.info("event=venta_cancel_success ventaId={} estadoAnterior={} estadoActual={}",
                ventaAnulada.getId(), estadoAnterior, ventaAnulada.getEstado());
        return ventaAnulada;
    }
}
