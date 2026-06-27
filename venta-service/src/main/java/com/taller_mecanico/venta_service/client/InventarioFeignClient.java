package com.taller_mecanico.venta_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventario-service")
public interface InventarioFeignClient {

    @PutMapping("/inventario/{id}/descontar")
    void descontarStock(@PathVariable("id") Long id, @RequestParam("cantidad") Integer cantidad);
}
