package com.taller_mecanico.vehiculo_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller_mecanico.vehiculo_service.entity.Vehiculo;

public interface VehiculoRepository
        extends JpaRepository<Vehiculo, Long> {

    Optional<Vehiculo> findByPatente(String patente);

    List<Vehiculo> findByClienteId(Long clienteId);
}