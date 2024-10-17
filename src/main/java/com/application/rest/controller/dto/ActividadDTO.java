package com.application.rest.controller.dto;

import com.application.rest.entities.Asignacion;
import com.application.rest.entities.Inconveniente;
import com.application.rest.entities.Proyecto;
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
public class ActividadDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer tiempoPlaneado;
    private Integer tiempoEmpleado;// Foreign key
    private Proyecto proyecto;
    private List<Inconveniente> inconvenientes;
    private List<Asignacion> asignaciones;
}
