package com.taller_mecanico.security_service.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void debeCrearRespuestaExitosa() {
        ApiResponse<String> response = new ApiResponse<>(true, "Operación correcta", "OK");

        assertTrue(response.isSuccess());
        assertEquals("Operación correcta", response.getMessage());
        assertEquals("OK", response.getData());
    }

    @Test
    void debePermitirModificarRespuesta() {
        ApiResponse<String> response = new ApiResponse<>();

        response.setSuccess(false);
        response.setMessage("Error controlado");
        response.setData(null);

        assertFalse(response.isSuccess());
        assertEquals("Error controlado", response.getMessage());
        assertNull(response.getData());
    }
}
