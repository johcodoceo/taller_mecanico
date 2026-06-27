package com.taller_mecanico.auth_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taller_mecanico.auth_service.entity.UsuarioAuth;

public interface UsuarioRepository extends JpaRepository<UsuarioAuth, Long> {

    Optional<UsuarioAuth> findByUsername(String username);
}
