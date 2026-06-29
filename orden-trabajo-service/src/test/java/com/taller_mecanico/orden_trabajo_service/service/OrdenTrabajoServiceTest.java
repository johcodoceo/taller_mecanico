package com.taller_mecanico.orden_trabajo_service.service;

import com.taller_mecanico.orden_trabajo_service.client.MecanicoFeignClient;
import com.taller_mecanico.orden_trabajo_service.client.VehiculoFeignClient;
import com.taller_mecanico.orden_trabajo_service.dto.MecanicoDTO;
import com.taller_mecanico.orden_trabajo_service.dto.OrdenTrabajoRequest;
import com.taller_mecanico.orden_trabajo_service.dto.VehiculoDTO;
import com.taller_mecanico.orden_trabajo_service.entity.OrdenTrabajo;
import com.taller_mecanico.orden_trabajo_service.enums.EstadoOrden;
import com.taller_mecanico.orden_trabajo_service.repository.OrdenTrabajoRepository;
import com.taller_mecanico.orden_trabajo_service.util.ApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrdenTrabajoServiceTest {

    @Mock
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Mock
    private VehiculoFeignClient vehiculoFeignClient;

    @Mock
    private MecanicoFeignClient mecanicoFeignClient;

    @InjectMocks
    private OrdenTrabajoService ordenTrabajoService;

    @Test
    void crearOrden_CuandoVehiculoYMecanicoExisten_GuardaOrdenPendiente() {
        OrdenTrabajoRequest request = new OrdenTrabajoRequest();
        request.setVehiculoId(1L);
        request.setMecanicoId(2L);
        request.setDiagnostico("Cambio de pastillas");
        request.setDescripcion("Revisión sistema de frenos");
        request.setCostoManoObra(45000.0);

        VehiculoDTO vehiculo = new VehiculoDTO();
        vehiculo.setId(1L);

        MecanicoDTO mecanico = new MecanicoDTO();
        mecanico.setId(2L);
        mecanico.setActivo(true);

        when(vehiculoFeignClient.buscarVehiculo(1L)).thenReturn(new ApiResponse<>(true, "OK", vehiculo));
        when(mecanicoFeignClient.buscarMecanico(2L)).thenReturn(new ApiResponse<>(true, "OK", mecanico));
        when(ordenTrabajoRepository.save(any(OrdenTrabajo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OrdenTrabajo resultado = ordenTrabajoService.crearOrden(request);

        assertEquals(EstadoOrden.PENDIENTE, resultado.getEstado());
        assertEquals(1L, resultado.getVehiculoId());
        assertEquals(2L, resultado.getMecanicoId());
        assertNotNull(resultado.getFechaIngreso());
        verify(ordenTrabajoRepository).save(any(OrdenTrabajo.class));
    }

    @Test
    void crearOrden_CuandoMecanicoEstaInactivo_LanzaIllegalArgumentException() {
        OrdenTrabajoRequest request = new OrdenTrabajoRequest();
        request.setVehiculoId(1L);
        request.setMecanicoId(2L);

        VehiculoDTO vehiculo = new VehiculoDTO();
        vehiculo.setId(1L);

        MecanicoDTO mecanico = new MecanicoDTO();
        mecanico.setId(2L);
        mecanico.setActivo(false);

        when(vehiculoFeignClient.buscarVehiculo(1L)).thenReturn(new ApiResponse<>(true, "OK", vehiculo));
        when(mecanicoFeignClient.buscarMecanico(2L)).thenReturn(new ApiResponse<>(true, "OK", mecanico));

        assertThrows(IllegalArgumentException.class, () -> ordenTrabajoService.crearOrden(request));

        verify(ordenTrabajoRepository, never()).save(any(OrdenTrabajo.class));
    }
}
