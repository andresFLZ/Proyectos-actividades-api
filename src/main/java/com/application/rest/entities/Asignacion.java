package com.application.rest.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "asignaciones")
public class Asignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_responsables")  // Foreign key
    private Responsable responsable;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_actividades")  // Foreign key
    private Actividad actividad;
}
