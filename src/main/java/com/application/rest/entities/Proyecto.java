package com.application.rest.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "proyectos")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 35, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 50, nullable = false)
    private String descripcion;
}