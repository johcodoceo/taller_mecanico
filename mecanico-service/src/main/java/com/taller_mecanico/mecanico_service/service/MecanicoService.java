package com.taller_mecanico.mecanico_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taller_mecanico.mecanico_service.dto.MecanicoRequest;
import com.taller_mecanico.mecanico_service.entity.Mecanico;
import com.taller_mecanico.mecanico_service.exception.ResourceNotFoundException;
import com.taller_mecanico.mecanico_service.repository.MecanicoRepository;

@Service
public class MecanicoService {

    private static final Logger logger = LoggerFactory.getLogger(MecanicoService.class);

    private final MecanicoRepository mecanicoRepository;

    public MecanicoService(MecanicoRepository mecanicoRepository) {
        this.mecanicoRepository = mecanicoRepository;
    }

    public Mecanico crear(MecanicoRequest request) {
        logger.info("event=mecanico_create_start rut={} especialidad={}", request.getRut(), request.getEspecialidad());

        if (mecanicoRepository.findByRut(request.getRut()).isPresent()) {
            logger.warn("event=mecanico_create_rejected reason=rut_already_exists rut={}", request.getRut());
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

        Mecanico mecanicoGuardado = mecanicoRepository.save(mecanico);
        logger.info("event=mecanico_create_success mecanicoId={} rut={} activo={}",
                mecanicoGuardado.getId(), mecanicoGuardado.getRut(), mecanicoGuardado.getActivo());

        return mecanicoGuardado;
    }

    public List<Mecanico> listar() {
        logger.info("event=mecanico_list_start");
        List<Mecanico> mecanicos = mecanicoRepository.findAll();
        logger.info("event=mecanico_list_success total={}", mecanicos.size());
        return mecanicos;
    }

    public List<Mecanico> listarActivos() {
        logger.info("event=mecanico_list_active_start");
        List<Mecanico> mecanicos = mecanicoRepository.findByActivoTrue();
        logger.info("event=mecanico_list_active_success total={}", mecanicos.size());
        return mecanicos;
    }

    public Mecanico buscarPorId(Long id) {
        logger.info("event=mecanico_find_by_id_start mecanicoId={}", id);
        return mecanicoRepository.findById(id)
                .map(mecanico -> {
                    logger.info("event=mecanico_find_by_id_success mecanicoId={} rut={} activo={}",
                            mecanico.getId(), mecanico.getRut(), mecanico.getActivo());
                    return mecanico;
                })
                .orElseThrow(() -> {
                    logger.warn("event=mecanico_find_by_id_not_found mecanicoId={}", id);
                    return new ResourceNotFoundException("Mecánico no encontrado con id: " + id);
                });
    }

    public Mecanico actualizar(Long id, MecanicoRequest request) {
        logger.info("event=mecanico_update_start mecanicoId={} rut={}", id, request.getRut());

        Mecanico mecanico = buscarPorId(id);
        mecanico.setRut(request.getRut());
        mecanico.setNombre(request.getNombre());
        mecanico.setApellido(request.getApellido());
        mecanico.setEspecialidad(request.getEspecialidad());
        mecanico.setTelefono(request.getTelefono());
        mecanico.setCorreo(request.getCorreo());

        Mecanico mecanicoActualizado = mecanicoRepository.save(mecanico);
        logger.info("event=mecanico_update_success mecanicoId={} rut={} activo={}",
                mecanicoActualizado.getId(), mecanicoActualizado.getRut(), mecanicoActualizado.getActivo());

        return mecanicoActualizado;
    }

    public Mecanico desactivar(Long id) {
        logger.info("event=mecanico_deactivate_start mecanicoId={}", id);
        Mecanico mecanico = buscarPorId(id);
        mecanico.setActivo(false);
        Mecanico mecanicoActualizado = mecanicoRepository.save(mecanico);
        logger.info("event=mecanico_deactivate_success mecanicoId={} activo={}",
                mecanicoActualizado.getId(), mecanicoActualizado.getActivo());
        return mecanicoActualizado;
    }

    public Mecanico reactivar(Long id) {
        logger.info("event=mecanico_reactivate_start mecanicoId={}", id);
        Mecanico mecanico = buscarPorId(id);
        mecanico.setActivo(true);
        Mecanico mecanicoActualizado = mecanicoRepository.save(mecanico);
        logger.info("event=mecanico_reactivate_success mecanicoId={} activo={}",
                mecanicoActualizado.getId(), mecanicoActualizado.getActivo());
        return mecanicoActualizado;
    }
}
