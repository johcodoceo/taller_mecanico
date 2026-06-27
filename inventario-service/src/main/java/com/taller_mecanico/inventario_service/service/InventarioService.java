package com.taller_mecanico.inventario_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taller_mecanico.inventario_service.dto.RepuestoRequest;
import com.taller_mecanico.inventario_service.entity.Repuesto;
import com.taller_mecanico.inventario_service.exception.ResourceNotFoundException;
import com.taller_mecanico.inventario_service.repository.RepuestoRepository;

@Service
public class InventarioService {

    private final RepuestoRepository repuestoRepository;

    public InventarioService(
            RepuestoRepository repuestoRepository) {

        this.repuestoRepository =
                repuestoRepository;
    }

    public Repuesto crearRepuesto(
            RepuestoRequest request) {

        Repuesto repuesto = new Repuesto();

        repuesto.setCodigo(request.getCodigo());
        repuesto.setNombre(request.getNombre());
        repuesto.setDescripcion(request.getDescripcion());
        repuesto.setPrecio(request.getPrecio());
        repuesto.setStock(request.getStock());
        repuesto.setMarca(request.getMarca());

        return repuestoRepository.save(repuesto);
    }

    public List<Repuesto> listarRepuestos() {

        return repuestoRepository.findAll();
    }

    public Repuesto buscarPorId(Long id) {

        return repuestoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Repuesto no encontrado"));
    }


    public Repuesto aumentarStock(Long id, Integer cantidad) {

        if (cantidad == null || cantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        Repuesto repuesto = buscarPorId(id);

        repuesto.setStock(repuesto.getStock() + cantidad);

        return repuestoRepository.save(repuesto);
    }

    public Repuesto descontarStock(
            Long id,
            Integer cantidad) {

        if (cantidad == null || cantidad <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        Repuesto repuesto =
                buscarPorId(id);

        if (repuesto.getStock() < cantidad) {

            throw new RuntimeException(
                    "Stock insuficiente");
        }

        repuesto.setStock(
                repuesto.getStock() - cantidad);

        return repuestoRepository.save(repuesto);
    }
}
