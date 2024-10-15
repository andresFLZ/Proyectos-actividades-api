package com.application.rest.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en PostgreSQL
    private Integer id;

    @Column(name = "nombre", length = 40, nullable = false)  // 40 caracteres máx.
    private String nombre;

    @Column(name = "descripcion", length = 50, nullable = false)  // 50 caracteres máx.
    private String descripcion;

    @Column(name = "tiempo_planeado", nullable = false)
    private Integer tiempoPlaneado;

    @Column(name = "tiempo_empleado", nullable = false)
    private Integer tiempoEmpleado;
}
