package com.taller_mecanico.orden_trabajo_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.taller_mecanico.orden_trabajo_service.dto.UsuarioDTO;
import com.taller_mecanico.orden_trabajo_service.util.ApiResponse;

@FeignClient(name = "user-service")
public interface UsuarioFeignClient {

    @GetMapping("/users/{id}")
    ApiResponse<UsuarioDTO> buscarUsuario(@PathVariable Long id);
}
