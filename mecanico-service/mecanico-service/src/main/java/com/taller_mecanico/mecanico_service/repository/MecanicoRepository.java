package com.taller_mecanico.mecanico_service.repository;

import com.taller_mecanico.mecanico_service.entity.Mecanico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MecanicoRepository extends JpaRepository<Mecanico, Long> {
    Optional<Mecanico> findByRut(String rut);
    List<Mecanico> findByActivoTrue();
}
