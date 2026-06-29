package com.taller_mecanico.cliente_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taller_mecanico.cliente_service.dto.ClienteRequest;
import com.taller_mecanico.cliente_service.entity.Cliente;
import com.taller_mecanico.cliente_service.exception.ResourceNotFoundException;
import com.taller_mecanico.cliente_service.repository.ClienteRepository;

@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente crearCliente(ClienteRequest request) {
        logger.info("event=cliente_create_start rut={} correo={}", request.getRut(), request.getCorreo());

        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setRut(request.getRut());
        cliente.setCorreo(request.getCorreo());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());

        Cliente clienteGuardado = clienteRepository.save(cliente);
        logger.info("event=cliente_create_success clienteId={} rut={}", clienteGuardado.getId(), clienteGuardado.getRut());

        return clienteGuardado;
    }

    public List<Cliente> listarClientes() {
        logger.info("event=cliente_list_start");
        List<Cliente> clientes = clienteRepository.findAll();
        logger.info("event=cliente_list_success total={}", clientes.size());
        return clientes;
    }

    public Cliente buscarPorId(Long id) {
        logger.info("event=cliente_find_by_id_start clienteId={}", id);
        return clienteRepository.findById(id)
                .map(cliente -> {
                    logger.info("event=cliente_find_by_id_success clienteId={} rut={}", cliente.getId(), cliente.getRut());
                    return cliente;
                })
                .orElseThrow(() -> {
                    logger.warn("event=cliente_find_by_id_not_found clienteId={}", id);
                    return new ResourceNotFoundException("Cliente no encontrado");
                });
    }

    public Cliente buscarPorRut(String rut) {
        logger.info("event=cliente_find_by_rut_start rut={}", rut);
        return clienteRepository.findByRut(rut)
                .map(cliente -> {
                    logger.info("event=cliente_find_by_rut_success clienteId={} rut={}", cliente.getId(), rut);
                    return cliente;
                })
                .orElseThrow(() -> {
                    logger.warn("event=cliente_find_by_rut_not_found rut={}", rut);
                    return new ResourceNotFoundException("Cliente no encontrado");
                });
    }

    public Cliente actualizarCliente(Long id, ClienteRequest request) {
        logger.info("event=cliente_update_start clienteId={} rut={}", id, request.getRut());

        Cliente cliente = buscarPorId(id);
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setRut(request.getRut());
        cliente.setCorreo(request.getCorreo());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());

        Cliente clienteActualizado = clienteRepository.save(cliente);
        logger.info("event=cliente_update_success clienteId={} rut={}", clienteActualizado.getId(), clienteActualizado.getRut());

        return clienteActualizado;
    }

    public void eliminarCliente(Long id) {
        logger.info("event=cliente_delete_start clienteId={}", id);
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
        logger.info("event=cliente_delete_success clienteId={} rut={}", cliente.getId(), cliente.getRut());
    }
}
