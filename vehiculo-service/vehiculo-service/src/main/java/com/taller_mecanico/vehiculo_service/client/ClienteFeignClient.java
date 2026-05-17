package com.taller_mecanico.vehiculo_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.taller_mecanico.vehiculo_service.dto.ClienteDTO;
import com.taller_mecanico.vehiculo_service.util.ApiResponse;

@FeignClient(name = "cliente-service")
public interface ClienteFeignClient {

    @GetMapping("/clientes/{id}")
    ApiResponse<ClienteDTO> buscarCliente(@PathVariable Long id);
}
