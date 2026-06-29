package com.taller_mecanico.vehiculo_service.service;

import com.taller_mecanico.vehiculo_service.client.ClienteFeignClient;
import com.taller_mecanico.vehiculo_service.dto.ClienteDTO;
import com.taller_mecanico.vehiculo_service.dto.VehiculoRequest;
import com.taller_mecanico.vehiculo_service.entity.Vehiculo;
import com.taller_mecanico.vehiculo_service.exception.ResourceNotFoundException;
import com.taller_mecanico.vehiculo_service.repository.VehiculoRepository;
import com.taller_mecanico.vehiculo_service.util.ApiResponse;
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
class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private ClienteFeignClient clienteFeignClient;

    @InjectMocks
    private VehiculoService vehiculoService;

    @Test
    void crearVehiculo_CuandoClienteExiste_GuardaVehiculo() {
        VehiculoRequest request = new VehiculoRequest();
        request.setPatente("ABCD12");
        request.setMarca("Kia");
        request.setModelo("Sportage");
        request.setAnio(2026);
        request.setColor("Gris");
        request.setClienteId(1L);

        ClienteDTO cliente = new ClienteDTO();
        cliente.setId(1L);

        when(clienteFeignClient.buscarCliente(1L)).thenReturn(new ApiResponse<>(true, "OK", cliente));
        when(vehiculoRepository.save(any(Vehiculo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Vehiculo resultado = vehiculoService.crearVehiculo(request);

        assertEquals("ABCD12", resultado.getPatente());
        assertEquals(1L, resultado.getClienteId());
        verify(clienteFeignClient).buscarCliente(1L);
        verify(vehiculoRepository).save(any(Vehiculo.class));
    }

    @Test
    void crearVehiculo_CuandoClienteNoExiste_LanzaResourceNotFoundException() {
        VehiculoRequest request = new VehiculoRequest();
        request.setClienteId(99L);

        when(clienteFeignClient.buscarCliente(99L)).thenReturn(new ApiResponse<>(false, "No encontrado", null));

        assertThrows(ResourceNotFoundException.class, () -> vehiculoService.crearVehiculo(request));

        verify(vehiculoRepository, never()).save(any(Vehiculo.class));
    }
}
