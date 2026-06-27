package com.taller_mecanico.auth_service.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.taller_mecanico.auth_service.dto.LoginRequest;
import com.taller_mecanico.auth_service.dto.RegisterRequest;
import com.taller_mecanico.auth_service.entity.UsuarioAuth;
import com.taller_mecanico.auth_service.repository.UsuarioRepository;
import com.taller_mecanico.auth_service.security.JwtUtil;

@Service
public class AuthService {

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

        UsuarioAuth usuario = new UsuarioAuth();

        usuario.setUsername(request.getUsername());

        usuario.setPasswordHash(
                passwordEncoder.encode(request.getPassword()));

        usuario.setRol(request.getRol());

        usuarioRepository.save(usuario);

        return "Usuario registrado";
    }

    public String login(LoginRequest request) {

        UsuarioAuth usuario = usuarioRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuario no encontrado"));

        boolean passwordCorrecta =
                passwordEncoder.matches(
                        request.getPassword(),
                        usuario.getPasswordHash());

        if (!passwordCorrecta) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        return jwtUtil.generateToken(usuario.getUsername(), usuario.getRol());
    }
}