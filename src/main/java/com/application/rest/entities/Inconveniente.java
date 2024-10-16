package com.application.rest.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "inconvenientes")
public class Inconveniente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descripcion", length = 50, nullable = false)
    private String descripcion;

    // Si se crea un inconveniente con un tipo de impacto que no este en la db se crea el tipo de impacto
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_tipos_impacto", nullable = false)  // Foreign key
    private TipoImpacto tipoImpacto;
}
