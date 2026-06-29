package com.taller_mecanico.inventario_service.service;

import com.taller_mecanico.inventario_service.entity.Repuesto;
import com.taller_mecanico.inventario_service.repository.RepuestoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private RepuestoRepository repuestoRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    void descontarStock_CuandoHayStock_RestaCantidadYGuarda() {
        Repuesto repuesto = new Repuesto();
        repuesto.setId(1L);
        repuesto.setNombre("Filtro de aceite");
        repuesto.setStock(10);

        when(repuestoRepository.findById(1L)).thenReturn(Optional.of(repuesto));
        when(repuestoRepository.save(any(Repuesto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Repuesto resultado = inventarioService.descontarStock(1L, 3);

        assertEquals(7, resultado.getStock());
        verify(repuestoRepository).findById(1L);
        verify(repuestoRepository).save(repuesto);
    }

    @Test
    void descontarStock_CuandoNoHayStockSuficiente_LanzaError() {
        Repuesto repuesto = new Repuesto();
        repuesto.setId(1L);
        repuesto.setStock(2);

        when(repuestoRepository.findById(1L)).thenReturn(Optional.of(repuesto));

        RuntimeException error = assertThrows(RuntimeException.class, () -> inventarioService.descontarStock(1L, 5));

        assertEquals("Stock insuficiente", error.getMessage());
        verify(repuestoRepository, never()).save(any(Repuesto.class));
    }
}
