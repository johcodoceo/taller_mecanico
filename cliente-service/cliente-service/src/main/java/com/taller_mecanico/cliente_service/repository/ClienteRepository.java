package com.taller_mecanico.cliente_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller_mecanico.cliente_service.entity.Cliente;

public interface ClienteRepository
        extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByRut(String rut);
}
