package com.taller_mecanico.auth_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.taller_mecanico.auth_service.dto.LoginRequest;
import com.taller_mecanico.auth_service.dto.RegisterRequest;
import com.taller_mecanico.auth_service.entity.UsuarioAuth;
import com.taller_mecanico.auth_service.repository.UsuarioRepository;
import com.taller_mecanico.auth_service.security.JwtUtil;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(
            UsuarioRepository usuarioRepository,
            BCryptPasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(RegisterRequest request) {
        logger.info("event=auth_register_start username={} rol={}", request.getUsername(), request.getRol());

        UsuarioAuth usuario = new UsuarioAuth();
        usuario.setUsername(request.getUsername());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(request.getRol());

        UsuarioAuth usuarioGuardado = usuarioRepository.save(usuario);

        logger.info("event=auth_register_success usuarioId={} username={} rol={}",
                usuarioGuardado.getId(), usuarioGuardado.getUsername(), usuarioGuardado.getRol());

        return "Usuario registrado";
    }

    public String login(LoginRequest request) {
        logger.info("event=auth_login_start username={}", request.getUsername());

        UsuarioAuth usuario = usuarioRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    logger.warn("event=auth_login_failed reason=user_not_found username={}", request.getUsername());
                    return new IllegalArgumentException("Usuario no encontrado");
                });

        boolean passwordCorrecta = passwordEncoder.matches(
                request.getPassword(),
                usuario.getPasswordHash());

        if (!passwordCorrecta) {
            logger.warn("event=auth_login_failed reason=invalid_password username={}", request.getUsername());
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol());
        logger.info("event=auth_login_success usuarioId={} username={} rol={}",
                usuario.getId(), usuario.getUsername(), usuario.getRol());

        return token;
    }
}
