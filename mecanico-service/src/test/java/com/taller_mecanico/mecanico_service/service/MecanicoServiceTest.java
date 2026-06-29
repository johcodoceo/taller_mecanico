package com.taller_mecanico.mecanico_service.service;

import com.taller_mecanico.mecanico_service.dto.MecanicoRequest;
import com.taller_mecanico.mecanico_service.entity.Mecanico;
import com.taller_mecanico.mecanico_service.repository.MecanicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MecanicoServiceTest {

    @Mock
    private MecanicoRepository mecanicoRepository;

    @InjectMocks
    private MecanicoService mecanicoService;

    @Test
    void crear_CuandoRutNoExiste_GuardaMecanicoActivo() {
        MecanicoRequest request = new MecanicoRequest();
        request.setRut("22.222.222-2");
        request.setNombre("Carlos");
        request.setApellido("Rojas");
        request.setEspecialidad("Frenos");
        request.setTelefono("977777777");
        request.setCorreo("carlos@test.cl");

        when(mecanicoRepository.findByRut("22.222.222-2")).thenReturn(Optional.empty());
        when(mecanicoRepository.save(any(Mecanico.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Mecanico resultado = mecanicoService.crear(request);

        assertEquals("Carlos", resultado.getNombre());
        assertTrue(resultado.getActivo());
        verify(mecanicoRepository).findByRut("22.222.222-2");
        verify(mecanicoRepository).save(any(Mecanico.class));
    }

    @Test
    void crear_CuandoRutExiste_LanzaIllegalArgumentException() {
        Mecanico existente = new Mecanico();
        existente.setRut("22.222.222-2");

        MecanicoRequest request = new MecanicoRequest();
        request.setRut("22.222.222-2");

        when(mecanicoRepository.findByRut("22.222.222-2")).thenReturn(Optional.of(existente));

        assertThrows(IllegalArgumentException.class, () -> mecanicoService.crear(request));

        verify(mecanicoRepository, never()).save(any(Mecanico.class));
    }
}
