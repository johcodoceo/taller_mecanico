package com.taller_mecanico.cliente_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taller_mecanico.cliente_service.dto.ClienteRequest;
import com.taller_mecanico.cliente_service.entity.Cliente;
import com.taller_mecanico.cliente_service.exception.ResourceNotFoundException;
import com.taller_mecanico.cliente_service.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(
            ClienteRepository clienteRepository) {

        this.clienteRepository = clienteRepository;
    }

    public Cliente crearCliente(
            ClienteRequest request) {

        Cliente cliente = new Cliente();

        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setRut(request.getRut());
        cliente.setCorreo(request.getCorreo());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());

        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {

        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {

        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente no encontrado"));
    }

    public Cliente buscarPorRut(String rut) {

        return clienteRepository.findByRut(rut)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente no encontrado"));
    }

    public Cliente actualizarCliente(
            Long id,
            ClienteRequest request) {

        Cliente cliente = buscarPorId(id);

        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setRut(request.getRut());
        cliente.setCorreo(request.getCorreo());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());

        return clienteRepository.save(cliente);
    }

    public void eliminarCliente(Long id) {

        Cliente cliente = buscarPorId(id);

        clienteRepository.delete(cliente);
    }
}
