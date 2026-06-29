package com.taller_mecanico.vehiculo_service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(VehiculoService.class);

    private final VehiculoRepository vehiculoRepository;
    private final ClienteFeignClient clienteFeignClient;

    public VehiculoService(VehiculoRepository vehiculoRepository, ClienteFeignClient clienteFeignClient) {
        this.vehiculoRepository = vehiculoRepository;
        this.clienteFeignClient = clienteFeignClient;
    }

    public Vehiculo crearVehiculo(VehiculoRequest request) {
        logger.info("event=vehiculo_create_start patente={} clienteId={}", request.getPatente(), request.getClienteId());

        ApiResponse<ClienteDTO> response = clienteFeignClient.buscarCliente(request.getClienteId());

        if (response == null || response.getData() == null) {
            logger.warn("event=vehiculo_create_rejected reason=cliente_not_found clienteId={}", request.getClienteId());
            throw new ResourceNotFoundException("Cliente no existe");
        }

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPatente(request.getPatente());
        vehiculo.setMarca(request.getMarca());
        vehiculo.setModelo(request.getModelo());
        vehiculo.setAnio(request.getAnio());
        vehiculo.setColor(request.getColor());
        vehiculo.setClienteId(request.getClienteId());

        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);
        logger.info("event=vehiculo_create_success vehiculoId={} patente={} clienteId={}",
                vehiculoGuardado.getId(), vehiculoGuardado.getPatente(), vehiculoGuardado.getClienteId());

        return vehiculoGuardado;
    }

    public List<Vehiculo> listarVehiculos() {
        logger.info("event=vehiculo_list_start");
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        logger.info("event=vehiculo_list_success total={}", vehiculos.size());
        return vehiculos;
    }

    public List<Vehiculo> buscarPorCliente(Long clienteId) {
        logger.info("event=vehiculo_find_by_cliente_start clienteId={}", clienteId);
        List<Vehiculo> vehiculos = vehiculoRepository.findByClienteId(clienteId);
        logger.info("event=vehiculo_find_by_cliente_success clienteId={} total={}", clienteId, vehiculos.size());
        return vehiculos;
    }

    public Vehiculo buscarPorId(Long id) {
        logger.info("event=vehiculo_find_by_id_start vehiculoId={}", id);
        return vehiculoRepository.findById(id)
                .map(vehiculo -> {
                    logger.info("event=vehiculo_find_by_id_success vehiculoId={} patente={} clienteId={}",
                            vehiculo.getId(), vehiculo.getPatente(), vehiculo.getClienteId());
                    return vehiculo;
                })
                .orElseThrow(() -> {
                    logger.warn("event=vehiculo_find_by_id_not_found vehiculoId={}", id);
                    return new ResourceNotFoundException("Vehículo no encontrado");
                });
    }
}
