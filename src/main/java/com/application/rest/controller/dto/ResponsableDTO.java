package com.application.rest.controller.dto;

import com.application.rest.entities.Asignacion;
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
public class ResponsableDTO {

    private Integer id;
    private String nombre;
    private String email;
    private List<Asignacion> asignaciones;
}
