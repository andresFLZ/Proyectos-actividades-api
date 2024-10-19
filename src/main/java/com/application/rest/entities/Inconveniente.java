package com.application.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inconvenientes")
public class Inconveniente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descripcion", length = 50, nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_tipos_impacto")  // Foreign key
    private TipoImpacto tipoImpacto;

    @ManyToOne
    @JoinColumn(name = "id_actividades")  // Foreign key
    @JsonIgnore
    private Actividad actividad;
}
