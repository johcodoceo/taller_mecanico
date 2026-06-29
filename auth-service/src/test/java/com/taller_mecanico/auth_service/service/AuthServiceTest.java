package com.taller_mecanico.auth_service.service;

import com.taller_mecanico.auth_service.dto.LoginRequest;
import com.taller_mecanico.auth_service.dto.RegisterRequest;
import com.taller_mecanico.auth_service.entity.UsuarioAuth;
import com.taller_mecanico.auth_service.repository.UsuarioRepository;
import com.taller_mecanico.auth_service.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_EncriptaPasswordYGuardaUsuario() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("admin");
        request.setPassword("secreto123");
        request.setRol("ADMIN");

        when(passwordEncoder.encode("secreto123")).thenReturn("hash-password");
        when(usuarioRepository.save(any(UsuarioAuth.class)))
                .thenAnswer(invocation -> {
                    UsuarioAuth usuarioGuardado = invocation.getArgument(0);
                    usuarioGuardado.setId(1L);
                    return usuarioGuardado;
                });

        String resultado = authService.register(request);

        assertEquals("Usuario registrado", resultado);
        verify(passwordEncoder).encode("secreto123");
        verify(usuarioRepository).save(any(UsuarioAuth.class));
    }

    @Test
    void login_CuandoCredencialesSonCorrectas_RetornaToken() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("secreto123");

        UsuarioAuth usuario = new UsuarioAuth();
        usuario.setUsername("admin");
        usuario.setPasswordHash("hash-password");
        usuario.setRol("ADMIN");

        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("secreto123", "hash-password")).thenReturn(true);
        when(jwtUtil.generateToken("admin", "ADMIN")).thenReturn("jwt-token");

        String token = authService.login(request);

        assertEquals("jwt-token", token);
        verify(jwtUtil).generateToken("admin", "ADMIN");
    }

    @Test
    void login_CuandoPasswordEsIncorrecta_LanzaIllegalArgumentException() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("mal-password");

        UsuarioAuth usuario = new UsuarioAuth();
        usuario.setUsername("admin");
        usuario.setPasswordHash("hash-password");

        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("mal-password", "hash-password")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> authService.login(request));

        verify(jwtUtil, never()).generateToken(any(), any());
    }
}
