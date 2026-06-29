package com.taller_mecanico.venta_service.service;

import com.taller_mecanico.venta_service.client.InventarioFeignClient;
import com.taller_mecanico.venta_service.dto.VentaDetalleRequest;
import com.taller_mecanico.venta_service.dto.VentaRequest;
import com.taller_mecanico.venta_service.entity.Venta;
import com.taller_mecanico.venta_service.repository.VentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private InventarioFeignClient inventarioFeignClient;

    @InjectMocks
    private VentaService ventaService;

    @Test
    void crearVenta_CalculaTotalDescuentaStockYGuardaVenta() {
        VentaDetalleRequest detalleUno = new VentaDetalleRequest();
        detalleUno.setRepuestoId(1L);
        detalleUno.setCantidad(2);
        detalleUno.setPrecioUnitario(10000.0);

        VentaDetalleRequest detalleDos = new VentaDetalleRequest();
        detalleDos.setRepuestoId(2L);
        detalleDos.setCantidad(1);
        detalleDos.setPrecioUnitario(15000.0);

        VentaRequest request = new VentaRequest();
        request.setClienteId(9L);
        request.setDetalles(List.of(detalleUno, detalleDos));

        when(ventaRepository.save(any(Venta.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Venta resultado = ventaService.crearVenta(request);

        assertEquals(9L, resultado.getClienteId());
        assertEquals("REGISTRADA", resultado.getEstado());
        assertEquals(35000.0, resultado.getTotal());
        assertEquals(2, resultado.getDetalles().size());
        assertNotNull(resultado.getFechaVenta());
        verify(inventarioFeignClient).descontarStock(1L, 2);
        verify(inventarioFeignClient).descontarStock(2L, 1);
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }
}
