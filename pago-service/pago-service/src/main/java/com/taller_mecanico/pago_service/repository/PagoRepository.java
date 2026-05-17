package com.taller_mecanico.pago_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller_mecanico.pago_service.entity.Pago;

public interface PagoRepository
        extends JpaRepository<Pago, Long> {
}
