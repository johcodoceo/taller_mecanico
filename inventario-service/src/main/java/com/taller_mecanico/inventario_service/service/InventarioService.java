package com.taller_mecanico.inventario_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taller_mecanico.inventario_service.dto.RepuestoRequest;
import com.taller_mecanico.inventario_service.entity.Repuesto;
import com.taller_mecanico.inventario_service.exception.ResourceNotFoundException;
import com.taller_mecanico.inventario_service.repository.RepuestoRepository;

@Service
public class InventarioService {

    private static final Logger logger = LoggerFactory.getLogger(InventarioService.class);

    private final RepuestoRepository repuestoRepository;

    public InventarioService(RepuestoRepository repuestoRepository) {
        this.repuestoRepository = repuestoRepository;
    }

    public Repuesto crearRepuesto(RepuestoRequest request) {
        logger.info("event=repuesto_create_start codigo={} nombre={} stock={}",
                request.getCodigo(), request.getNombre(), request.getStock());

        Repuesto repuesto = new Repuesto();
        repuesto.setCodigo(request.getCodigo());
        repuesto.setNombre(request.getNombre());
        repuesto.setDescripcion(request.getDescripcion());
        repuesto.setPrecio(request.getPrecio());
        repuesto.setStock(request.getStock());
        repuesto.setMarca(request.getMarca());

        Repuesto repuestoGuardado = repuestoRepository.save(repuesto);
        logger.info("event=repuesto_create_success repuestoId={} codigo={} stock={}",
                repuestoGuardado.getId(), repuestoGuardado.getCodigo(), repuestoGuardado.getStock());

        return repuestoGuardado;
    }

    public List<Repuesto> listarRepuestos() {
        logger.info("event=repuesto_list_start");
        List<Repuesto> repuestos = repuestoRepository.findAll();
        logger.info("event=repuesto_list_success total={}", repuestos.size());
        return repuestos;
    }

    public Repuesto buscarPorId(Long id) {
        logger.info("event=repuesto_find_by_id_start repuestoId={}", id);
        return repuestoRepository.findById(id)
                .map(repuesto -> {
                    logger.info("event=repuesto_find_by_id_success repuestoId={} codigo={}", repuesto.getId(), repuesto.getCodigo());
                    return repuesto;
                })
                .orElseThrow(() -> {
                    logger.warn("event=repuesto_find_by_id_not_found repuestoId={}", id);
                    return new ResourceNotFoundException("Repuesto no encontrado");
                });
    }

    public Repuesto aumentarStock(Long id, Integer cantidad) {
        logger.info("event=repuesto_stock_increase_start repuestoId={} cantidad={}", id, cantidad);

        if (cantidad == null || cantidad <= 0) {
            logger.warn("event=repuesto_stock_increase_rejected reason=invalid_quantity repuestoId={} cantidad={}", id, cantidad);
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        Repuesto repuesto = buscarPorId(id);
        Integer stockAnterior = repuesto.getStock();
        repuesto.setStock(repuesto.getStock() + cantidad);

        Repuesto repuestoActualizado = repuestoRepository.save(repuesto);
        logger.info("event=repuesto_stock_increase_success repuestoId={} stockAnterior={} stockActual={}",
                repuestoActualizado.getId(), stockAnterior, repuestoActualizado.getStock());

        return repuestoActualizado;
    }

    public Repuesto descontarStock(Long id, Integer cantidad) {
        logger.info("event=repuesto_stock_decrease_start repuestoId={} cantidad={}", id, cantidad);

        if (cantidad == null || cantidad <= 0) {
            logger.warn("event=repuesto_stock_decrease_rejected reason=invalid_quantity repuestoId={} cantidad={}", id, cantidad);
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        Repuesto repuesto = buscarPorId(id);

        if (repuesto.getStock() < cantidad) {
            logger.warn("event=repuesto_stock_decrease_rejected reason=insufficient_stock repuestoId={} stockActual={} cantidad={}",
                    id, repuesto.getStock(), cantidad);
            throw new RuntimeException("Stock insuficiente");
        }

        Integer stockAnterior = repuesto.getStock();
        repuesto.setStock(repuesto.getStock() - cantidad);

        Repuesto repuestoActualizado = repuestoRepository.save(repuesto);
        logger.info("event=repuesto_stock_decrease_success repuestoId={} stockAnterior={} stockActual={}",
                repuestoActualizado.getId(), stockAnterior, repuestoActualizado.getStock());

        return repuestoActualizado;
    }
}
