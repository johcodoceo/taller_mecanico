package com.taller_mecanico.orden_trabajo_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller_mecanico.orden_trabajo_service.entity.OrdenTrabajo;

public interface OrdenTrabajoRepository
        extends JpaRepository<OrdenTrabajo, Long> {

    List<OrdenTrabajo> findByVehiculoId(Long vehiculoId);

    List<OrdenTrabajo> findByMecanicoId(Long mecanicoId);
}
