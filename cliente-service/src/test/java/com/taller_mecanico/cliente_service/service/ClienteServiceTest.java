package com.taller_mecanico.cliente_service.service;

import com.taller_mecanico.cliente_service.dto.ClienteRequest;
import com.taller_mecanico.cliente_service.entity.Cliente;
import com.taller_mecanico.cliente_service.exception.ResourceNotFoundException;
import com.taller_mecanico.cliente_service.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void crearCliente_GuardaClienteConDatosDelRequest() {
        ClienteRequest request = new ClienteRequest();
        request.setNombre("Juan");
        request.setApellido("Perez");
        request.setRut("12.345.678-9");
        request.setCorreo("juan@test.cl");
        request.setTelefono("999999999");
        request.setDireccion("Av. Siempre Viva 123");

        when(clienteRepository.save(any(Cliente.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = clienteService.crearCliente(request);

        assertEquals("Juan", resultado.getNombre());
        assertEquals("Perez", resultado.getApellido());
        assertEquals("12.345.678-9", resultado.getRut());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void buscarPorId_CuandoNoExiste_LanzaResourceNotFoundException() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.buscarPorId(99L));

        verify(clienteRepository).findById(99L);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }
}
