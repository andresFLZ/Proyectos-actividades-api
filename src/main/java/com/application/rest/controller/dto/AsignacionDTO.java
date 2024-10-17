package com.application.rest.controller.dto;

import com.application.rest.entities.Actividad;
import com.application.rest.entities.Responsable;
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
public class AsignacionDTO {

    private Integer id;
    private Responsable responsable;
    private Actividad actividad;
}
