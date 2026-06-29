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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuarios", description = "Gestión administrativa de usuarios del sistema")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            summary = "Crear usuario",
            description = "Registra un nuevo usuario administrativo o funcional dentro del sistema."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuario creado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos del usuario inválidos")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Usuario>> crearUsuario(@Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = usuarioService.crearUsuario(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario creado correctamente", usuario));
    }

    @Operation(
            summary = "Listar usuarios",
            description = "Obtiene todos los usuarios registrados en el microservicio de usuarios."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<Usuario>>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios obtenidos correctamente", usuarios));
    }

    @Operation(
            summary = "Buscar usuario por ID",
            description = "Obtiene la información de un usuario específico mediante su identificador."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuario obtenido correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> buscarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario obtenido correctamente", usuario));
    }

    @Operation(
            summary = "Actualizar usuario",
            description = "Modifica los datos de un usuario existente."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos del usuario inválidos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Usuario>> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = usuarioService.actualizarUsuario(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario actualizado correctamente", usuario));
    }

    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario del sistema mediante su identificador."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario eliminado correctamente", null));
    }
}
