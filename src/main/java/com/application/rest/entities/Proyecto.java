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
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "proyectos")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 35, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 50, nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Actividad> actividades;
}
