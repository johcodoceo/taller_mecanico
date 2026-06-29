package com.taller_mecanico.user_service.service;

import com.taller_mecanico.user_service.dto.UsuarioRequest;
import com.taller_mecanico.user_service.entity.Usuario;
import com.taller_mecanico.user_service.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void crearUsuario_GuardaUsuarioConAuthId() {
        UsuarioRequest request = new UsuarioRequest();
        request.setNombre("Ana");
        request.setApellido("Soto");
        request.setCorreo("ana@test.cl");
        request.setTelefono("988888888");
        request.setDireccion("Calle 1");
        request.setRut("11.111.111-1");
        request.setAuthId(10L);

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.crearUsuario(request);

        assertEquals("Ana", resultado.getNombre());
        assertEquals(10L, resultado.getAuthId());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void buscarPorId_CuandoNoExiste_LanzaRuntimeException() {
        when(usuarioRepository.findById(50L)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> usuarioService.buscarPorId(50L));

        assertEquals("Usuario no encontrado", error.getMessage());
        verify(usuarioRepository).findById(50L);
    }
}
