package com.taller_mecanico.user_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taller_mecanico.user_service.dto.UsuarioRequest;
import com.taller_mecanico.user_service.entity.Usuario;
import com.taller_mecanico.user_service.service.UsuarioService;
import com.taller_mecanico.user_service.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Usuario>> crearUsuario(@Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = usuarioService.crearUsuario(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario creado correctamente", usuario));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Usuario>>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios obtenidos correctamente", usuarios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> buscarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario obtenido correctamente", usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = usuarioService.actualizarUsuario(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario actualizado correctamente", usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario eliminado correctamente", null));
    }
}
