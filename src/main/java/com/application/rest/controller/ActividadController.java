package com.application.rest.controller;

import com.application.rest.controller.dto.ActividadDTO;
import com.application.rest.entities.Actividad;
import com.application.rest.entities.Proyecto;
import com.application.rest.service.IActividadService;
import com.application.rest.service.IProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/actividad")
public class ActividadController {

    @Autowired
    IActividadService actividadService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<ActividadDTO> actividadesList = actividadService.findAll()
                .stream()
                .map(actividad -> ActividadDTO.builder()
                        .id(actividad.getId())
                        .nombre(actividad.getNombre())
                        .descripcion(actividad.getDescripcion())
                        .tiempoPlaneado(actividad.getTiempoPlaneado())
                        .tiempoEmpleado(actividad.getTiempoEmpleado())
                        .proyecto(actividad.getProyecto())
                        .inconvenientes(actividad.getInconvenientes())
                        .asignaciones(actividad.getAsignaciones())
                        .build()
                ).toList();

        return ResponseEntity.ok(actividadesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Optional<Actividad> actividadOptional = actividadService.findById(id);

        if (actividadOptional.isPresent()) {
            Actividad actividad = actividadOptional.get();

            ActividadDTO actividadDTO = ActividadDTO.builder()
                    .id(actividad.getId())
                    .nombre(actividad.getNombre())
                    .descripcion(actividad.getDescripcion())
                    .tiempoPlaneado(actividad.getTiempoPlaneado())
                    .tiempoEmpleado(actividad.getTiempoEmpleado())
                    .proyecto(actividad.getProyecto())
                    .inconvenientes(actividad.getInconvenientes())
                    .asignaciones(actividad.getAsignaciones())
                    .build();

            return ResponseEntity.ok(actividadDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ActividadDTO actividadDTO) throws URISyntaxException {

        if (actividadDTO.getNombre().isBlank() || actividadDTO.getDescripcion().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre o la descripción no deben estar vacíos.");
        }

        if (actividadDTO.getTiempoPlaneado() == null || actividadDTO.getTiempoPlaneado() <= 0 ||
                actividadDTO.getTiempoEmpleado() == null || actividadDTO.getTiempoEmpleado() < 0) {
            return ResponseEntity.badRequest().body("Los tiempos deben ser positivos y no nulos.");
        }

        actividadService.save(Actividad.builder()
                .nombre(actividadDTO.getNombre())
                .descripcion(actividadDTO.getDescripcion())
                .tiempoEmpleado(actividadDTO.getTiempoEmpleado())
                .tiempoPlaneado(actividadDTO.getTiempoPlaneado())
                .proyecto(actividadDTO.getProyecto())
                .build()
        );

        return ResponseEntity.created(new URI("/api/actividad")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,@RequestBody ActividadDTO actividadDTO) {

        Optional<Actividad> actividadOptional = actividadService.findById(id);

        if (actividadOptional.isPresent()) {
            Actividad actividad = actividadOptional.get();

            actividad.setNombre(actividadDTO.getNombre());
            actividad.setDescripcion(actividadDTO.getDescripcion());
            actividad.setTiempoEmpleado(actividadDTO.getTiempoEmpleado());
            actividad.setTiempoPlaneado(actividadDTO.getTiempoPlaneado());
            actividad.setProyecto(actividadDTO.getProyecto());
            actividadService.save(actividad);

            return ResponseEntity.ok("Actividad actualizada exitosamente");
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {

        if (id != null) {
            actividadService.deleteById(id);
            return ResponseEntity.ok("Actividad eliminada exitosamente");
        }

        return ResponseEntity.notFound().build();

    }

}
