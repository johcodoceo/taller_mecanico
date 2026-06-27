package com.taller_mecanico.inventario_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller_mecanico.inventario_service.entity.Repuesto;

public interface RepuestoRepository
        extends JpaRepository<Repuesto, Long> {

    Optional<Repuesto> findByCodigo(String codigo);
}
