package com.application.rest.controller.dto;

import com.application.rest.entities.Actividad;
import com.application.rest.entities.TipoImpacto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InconvenienteDTO {

    private Integer id;
    private String descripcion;
    private TipoImpacto tipoImpacto;
    private Actividad actividad;
}
