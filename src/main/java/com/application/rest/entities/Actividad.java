package com.application.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "id_proyectos")
    @JsonIgnore
    private Proyecto proyecto;

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Inconveniente> inconvenientes;

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Asignacion> asignaciones;
}
