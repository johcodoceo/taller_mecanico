package com.taller_mecanico.vehiculo_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taller_mecanico.vehiculo_service.client.ClienteFeignClient;
import com.taller_mecanico.vehiculo_service.dto.ClienteDTO;
import com.taller_mecanico.vehiculo_service.dto.VehiculoRequest;
import com.taller_mecanico.vehiculo_service.entity.Vehiculo;
import com.taller_mecanico.vehiculo_service.exception.ResourceNotFoundException;
import com.taller_mecanico.vehiculo_service.repository.VehiculoRepository;
import com.taller_mecanico.vehiculo_service.util.ApiResponse;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final ClienteFeignClient clienteFeignClient;

    public VehiculoService(VehiculoRepository vehiculoRepository, ClienteFeignClient clienteFeignClient) {
        this.vehiculoRepository = vehiculoRepository;
        this.clienteFeignClient = clienteFeignClient;
    }

    public Vehiculo crearVehiculo(VehiculoRequest request) {
        ApiResponse<ClienteDTO> response = clienteFeignClient.buscarCliente(request.getClienteId());

        if (response == null || response.getData() == null) {
            throw new ResourceNotFoundException("Cliente no existe");
        }

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPatente(request.getPatente());
        vehiculo.setMarca(request.getMarca());
        vehiculo.setModelo(request.getModelo());
        vehiculo.setAnio(request.getAnio());
        vehiculo.setColor(request.getColor());
        vehiculo.setClienteId(request.getClienteId());

        return vehiculoRepository.save(vehiculo);
    }

    public List<Vehiculo> listarVehiculos() {
        return vehiculoRepository.findAll();
    }

    public List<Vehiculo> buscarPorCliente(Long clienteId) {
        return vehiculoRepository.findByClienteId(clienteId);
    }

    public Vehiculo buscarPorId(Long id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));
    }
}
