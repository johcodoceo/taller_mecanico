package com.taller_mecanico.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taller_mecanico.auth_service.dto.LoginRequest;
import com.taller_mecanico.auth_service.dto.RegisterRequest;
import com.taller_mecanico.auth_service.service.AuthService;
import com.taller_mecanico.auth_service.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest request) {
        String resultado = authService.register(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario registrado correctamente", resultado));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Login realizado correctamente", token));
    }
}
