package com.taller_mecanico.pago_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.taller_mecanico.pago_service.dto.OrdenTrabajoDTO;
import com.taller_mecanico.pago_service.util.ApiResponse;

@FeignClient(name = "orden-trabajo-service")
public interface OrdenTrabajoFeignClient {

    @GetMapping("/ordenes/{id}")
    ApiResponse<OrdenTrabajoDTO> buscarOrden(@PathVariable Long id);
}
