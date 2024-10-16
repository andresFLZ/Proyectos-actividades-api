package com.application.rest.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 40, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 50, nullable = false)
    private String descripcion;

    @Column(name = "tiempo_planeado", nullable = false)
    private Integer tiempoPlaneado;

    @Column(name = "tiempo_empleado", nullable = false)
    private Integer tiempoEmpleado;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_proyectos")  // Foreign key
    private Proyecto proyecto;

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inconveniente> inconvenientes;

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asignacion> asignaciones;
}
