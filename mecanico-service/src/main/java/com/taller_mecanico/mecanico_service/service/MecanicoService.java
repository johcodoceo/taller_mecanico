package com.taller_mecanico.mecanico_service.service;

import com.taller_mecanico.mecanico_service.dto.MecanicoRequest;
import com.taller_mecanico.mecanico_service.entity.Mecanico;
import com.taller_mecanico.mecanico_service.exception.ResourceNotFoundException;
import com.taller_mecanico.mecanico_service.repository.MecanicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MecanicoService {

    private final MecanicoRepository mecanicoRepository;

    public MecanicoService(MecanicoRepository mecanicoRepository) {
        this.mecanicoRepository = mecanicoRepository;
    }

    public Mecanico crear(MecanicoRequest request) {
        if (mecanicoRepository.findByRut(request.getRut()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un mecánico con el rut indicado");
        }

        Mecanico mecanico = new Mecanico();
        mecanico.setRut(request.getRut());
        mecanico.setNombre(request.getNombre());
        mecanico.setApellido(request.getApellido());
        mecanico.setEspecialidad(request.getEspecialidad());
        mecanico.setTelefono(request.getTelefono());
        mecanico.setCorreo(request.getCorreo());
        mecanico.setActivo(true);
        return mecanicoRepository.save(mecanico);
    }

    public List<Mecanico> listar() {
        return mecanicoRepository.findAll();
    }

    public List<Mecanico> listarActivos() {
        return mecanicoRepository.findByActivoTrue();
    }

    public Mecanico buscarPorId(Long id) {
        return mecanicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mecánico no encontrado con id: " + id));
    }

    public Mecanico actualizar(Long id, MecanicoRequest request) {
        Mecanico mecanico = buscarPorId(id);
        mecanico.setRut(request.getRut());
        mecanico.setNombre(request.getNombre());
        mecanico.setApellido(request.getApellido());
        mecanico.setEspecialidad(request.getEspecialidad());
        mecanico.setTelefono(request.getTelefono());
        mecanico.setCorreo(request.getCorreo());
        return mecanicoRepository.save(mecanico);
    }

    public Mecanico desactivar(Long id) {
        Mecanico mecanico = buscarPorId(id);
        mecanico.setActivo(false);
        return mecanicoRepository.save(mecanico);
    }

    public Mecanico reactivar(Long id) {
        Mecanico mecanico = buscarPorId(id);
        mecanico.setActivo(true);
        return mecanicoRepository.save(mecanico);
    }
}