package com.taller_mecanico.venta_service.service;

import com.taller_mecanico.venta_service.client.InventarioFeignClient;
import com.taller_mecanico.venta_service.dto.VentaDetalleRequest;
import com.taller_mecanico.venta_service.dto.VentaRequest;
import com.taller_mecanico.venta_service.entity.Venta;
import com.taller_mecanico.venta_service.entity.VentaDetalle;
import com.taller_mecanico.venta_service.exception.ResourceNotFoundException;
import com.taller_mecanico.venta_service.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final InventarioFeignClient inventarioFeignClient;

    public VentaService(VentaRepository ventaRepository, InventarioFeignClient inventarioFeignClient) {
        this.ventaRepository = ventaRepository;
        this.inventarioFeignClient = inventarioFeignClient;
    }

    public Venta crearVenta(VentaRequest request) {
        Venta venta = new Venta();
        venta.setClienteId(request.getClienteId());
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEstado("REGISTRADA");

        double total = 0.0;

        for (VentaDetalleRequest detalleRequest : request.getDetalles()) {
            inventarioFeignClient.descontarStock(detalleRequest.getRepuestoId(), detalleRequest.getCantidad());

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
        return ventaRepository.save(venta);
    }

    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    public Venta buscarPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con id: " + id));
    }

    public List<Venta> listarPorCliente(Long clienteId) {
        return ventaRepository.findByClienteId(clienteId);
    }

    public Venta anular(Long id) {
        Venta venta = buscarPorId(id);
        venta.setEstado("ANULADA");
        return ventaRepository.save(venta);
    }
}
