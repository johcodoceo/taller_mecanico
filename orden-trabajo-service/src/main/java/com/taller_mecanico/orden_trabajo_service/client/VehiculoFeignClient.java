package com.taller_mecanico.orden_trabajo_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.taller_mecanico.orden_trabajo_service.dto.VehiculoDTO;
import com.taller_mecanico.orden_trabajo_service.util.ApiResponse;

@FeignClient(name = "vehiculo-service")
public interface VehiculoFeignClient {

    @GetMapping("/vehiculos/{id}")
    ApiResponse<VehiculoDTO> buscarVehiculo(@PathVariable Long id);
}
