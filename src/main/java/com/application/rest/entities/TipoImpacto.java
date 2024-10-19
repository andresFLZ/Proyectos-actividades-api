package com.application.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tipos_impacto")
public class TipoImpacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 30, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 50, nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "tipoImpacto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Inconveniente> inconvenientes;
}
