package com.application.rest.controller.dto;

import com.application.rest.entities.Inconveniente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoImpactoDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private List<Inconveniente> inconvenientes;
}
