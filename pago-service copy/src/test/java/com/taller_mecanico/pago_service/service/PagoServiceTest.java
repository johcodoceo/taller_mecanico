package com.taller_mecanico.pago_service.service;

import com.taller_mecanico.pago_service.client.OrdenTrabajoFeignClient;
import com.taller_mecanico.pago_service.dto.OrdenTrabajoDTO;
import com.taller_mecanico.pago_service.dto.PagoRequest;
import com.taller_mecanico.pago_service.entity.Pago;
import com.taller_mecanico.pago_service.enums.EstadoPago;
import com.taller_mecanico.pago_service.enums.MetodoPago;
import com.taller_mecanico.pago_service.exception.ResourceNotFoundException;
import com.taller_mecanico.pago_service.repository.PagoRepository;
import com.taller_mecanico.pago_service.util.ApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private OrdenTrabajoFeignClient ordenTrabajoFeignClient;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void crearPago_CuandoOrdenExiste_GuardaPagoPagadoConTotalDeLaOrden() {
        PagoRequest request = new PagoRequest();
        request.setOrdenTrabajoId(5L);
        request.setMetodoPago(MetodoPago.EFECTIVO);

        OrdenTrabajoDTO orden = new OrdenTrabajoDTO();
        orden.setId(5L);
        orden.setCostoManoObra(75000.0);

        when(ordenTrabajoFeignClient.buscarOrden(5L)).thenReturn(new ApiResponse<>(true, "OK", orden));
        when(pagoRepository.save(any(Pago.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pago resultado = pagoService.crearPago(request);

        assertEquals(EstadoPago.PAGADO, resultado.getEstado());
        assertEquals(MetodoPago.EFECTIVO, resultado.getMetodoPago());
        assertEquals(75000.0, resultado.getTotal());
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    void crearPago_CuandoOrdenNoExiste_LanzaResourceNotFoundException() {
        PagoRequest request = new PagoRequest();
        request.setOrdenTrabajoId(100L);
        request.setMetodoPago(MetodoPago.TRANSFERENCIA);

        when(ordenTrabajoFeignClient.buscarOrden(100L)).thenReturn(new ApiResponse<>(false, "No encontrada", null));

        assertThrows(ResourceNotFoundException.class, () -> pagoService.crearPago(request));

        verify(pagoRepository, never()).save(any(Pago.class));
    }
}
