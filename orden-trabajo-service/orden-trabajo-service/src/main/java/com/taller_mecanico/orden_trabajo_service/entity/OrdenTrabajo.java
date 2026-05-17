package com.taller_mecanico.orden_trabajo_service.entity;

import java.time.LocalDate;

import com.taller_mecanico.orden_trabajo_service.enums.EstadoOrden;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ordenes_trabajo")
@Data
public class OrdenTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaIngreso;

    private LocalDate fechaEntrega;

    @Column(length = 1000)
    private String diagnostico;

    @Column(length = 1000)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private EstadoOrden estado;

    private Double costoManoObra;

    // referencias lógicas
    private Long vehiculoId;

    private Long mecanicoId;
}