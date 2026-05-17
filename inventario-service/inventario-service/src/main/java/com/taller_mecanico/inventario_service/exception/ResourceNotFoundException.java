package com.taller_mecanico.inventario_service.exception;

public class ResourceNotFoundException
        extends RuntimeException {

    public ResourceNotFoundException(
            String message) {

        super(message);
    }
}
