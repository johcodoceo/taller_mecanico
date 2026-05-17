package com.taller_mecanico.orden_trabajo_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.taller_mecanico.orden_trabajo_service.dto.MecanicoDTO;
import com.taller_mecanico.orden_trabajo_service.util.ApiResponse;

@FeignClient(name = "mecanico-service")
public interface MecanicoFeignClient {

    @GetMapping("/mecanicos/{id}")
    ApiResponse<MecanicoDTO> buscarMecanico(@PathVariable Long id);
}
