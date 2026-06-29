package com.taller_mecanico.user_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taller_mecanico.user_service.dto.UsuarioRequest;
import com.taller_mecanico.user_service.entity.Usuario;
import com.taller_mecanico.user_service.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario crearUsuario(UsuarioRequest request) {
        logger.info("event=usuario_create_start rut={} correo={} authId={}",
                request.getRut(), request.getCorreo(), request.getAuthId());

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());
        usuario.setRut(request.getRut());
        usuario.setAuthId(request.getAuthId());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        logger.info("event=usuario_create_success usuarioId={} rut={} authId={}",
                usuarioGuardado.getId(), usuarioGuardado.getRut(), usuarioGuardado.getAuthId());

        return usuarioGuardado;
    }

    public List<Usuario> listarUsuarios() {
        logger.info("event=usuario_list_start");
        List<Usuario> usuarios = usuarioRepository.findAll();
        logger.info("event=usuario_list_success total={}", usuarios.size());
        return usuarios;
    }

    public Usuario buscarPorId(Long id) {
        logger.info("event=usuario_find_by_id_start usuarioId={}", id);
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    logger.info("event=usuario_find_by_id_success usuarioId={} rut={} authId={}",
                            usuario.getId(), usuario.getRut(), usuario.getAuthId());
                    return usuario;
                })
                .orElseThrow(() -> {
                    logger.warn("event=usuario_find_by_id_not_found usuarioId={}", id);
                    return new RuntimeException("Usuario no encontrado");
                });
    }

    public Usuario actualizarUsuario(Long id, UsuarioRequest request) {
        logger.info("event=usuario_update_start usuarioId={} rut={}", id, request.getRut());

        Usuario usuario = buscarPorId(id);
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());
        usuario.setRut(request.getRut());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        logger.info("event=usuario_update_success usuarioId={} rut={}",
                usuarioActualizado.getId(), usuarioActualizado.getRut());

        return usuarioActualizado;
    }

    public void eliminarUsuario(Long id) {
        logger.info("event=usuario_delete_start usuarioId={}", id);
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
        logger.info("event=usuario_delete_success usuarioId={} rut={}", usuario.getId(), usuario.getRut());
    }
}
