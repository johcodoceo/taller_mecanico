package com.taller_mecanico.user_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taller_mecanico.user_service.dto.UsuarioRequest;
import com.taller_mecanico.user_service.entity.Usuario;
import com.taller_mecanico.user_service.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository) {

        this.usuarioRepository = usuarioRepository;
    }

    public Usuario crearUsuario(
            UsuarioRequest request) {

        Usuario usuario = new Usuario();

        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());
        usuario.setRut(request.getRut());
        usuario.setAuthId(request.getAuthId());

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {

        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));
    }

    public Usuario actualizarUsuario(
            Long id,
            UsuarioRequest request) {

        Usuario usuario = buscarPorId(id);

        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());
        usuario.setRut(request.getRut());

        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id) {

        Usuario usuario = buscarPorId(id);

        usuarioRepository.delete(usuario);
    }
}
