package com.taller_mecanico.security_service.service;

import com.taller_mecanico.security_service.dto.SecurityEventRequest;
import com.taller_mecanico.security_service.entity.SecurityEvent;
import com.taller_mecanico.security_service.repository.SecurityEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityEventServiceTest {

    @Mock
    private SecurityEventRepository securityEventRepository;

    @InjectMocks
    private SecurityEventService securityEventService;

    @Test
    void registrar_GuardaEventoConFecha() {
        SecurityEventRequest request = new SecurityEventRequest();
        request.setUsuarioId(1L);
        request.setUsername("admin");
        request.setTipoEvento("LOGIN");
        request.setDescripcion("Inicio de sesión exitoso");
        request.setIpOrigen("127.0.0.1");
        request.setExitoso(true);

        when(securityEventRepository.save(any(SecurityEvent.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SecurityEvent resultado = securityEventService.registrar(request);

        assertEquals("admin", resultado.getUsername());
        assertEquals("LOGIN", resultado.getTipoEvento());
        assertNotNull(resultado.getFechaEvento());
        verify(securityEventRepository).save(any(SecurityEvent.class));
    }

    @Test
    void listarFallidos_ConsultaRepositorioPorEventosNoExitosos() {
        SecurityEvent fallido = new SecurityEvent();
        fallido.setUsername("admin");
        fallido.setExitoso(false);

        when(securityEventRepository.findByExitosoFalse()).thenReturn(List.of(fallido));

        List<SecurityEvent> resultado = securityEventService.listarFallidos();

        assertEquals(1, resultado.size());
        assertEquals(false, resultado.get(0).getExitoso());
        verify(securityEventRepository).findByExitosoFalse();
    }
}
