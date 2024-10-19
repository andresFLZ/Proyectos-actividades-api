package com.application.rest.controller;

import com.application.rest.controller.dto.AsignacionDTO;
import com.application.rest.entities.Asignacion;
import com.application.rest.service.IAsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asignacion")
public class AsignacionController {

    @Autowired
    IAsignacionService asignacionService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<AsignacionDTO> asignaciones = asignacionService.findAll()
                .stream().map(asignacion -> AsignacionDTO.builder()
                        .id(asignacion.getId())
                        .responsable(asignacion.getResponsable())
                        .actividad(asignacion.getActividad())
                        .build()
                ).toList();

        return ResponseEntity.ok(asignaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Optional<Asignacion> asignacionOptional = asignacionService.findById(id);

        if (asignacionOptional.isPresent()) {
            Asignacion asignacion = asignacionOptional.get();

            AsignacionDTO asignacionDTO = AsignacionDTO.builder()
                    .id(asignacion.getId())
                    .responsable(asignacion.getResponsable())
                    .actividad(asignacion.getActividad())
                    .build();

            return ResponseEntity.ok(asignacionDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AsignacionDTO asignacionDTO) throws URISyntaxException {

        asignacionService.save(Asignacion.builder()
                .responsable(asignacionDTO.getResponsable())
                .actividad(asignacionDTO.getActividad())
                .build()
        );

        return ResponseEntity.created(new URI("/api/asignacion")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody AsignacionDTO asignacionDTO) {

        Optional<Asignacion> asignacionOptional = asignacionService.findById(id);

        if (asignacionOptional.isPresent()) {
            Asignacion asignacion = asignacionOptional.get();

            asignacion.setResponsable(asignacionDTO.getResponsable());
            asignacion.setActividad(asignacionDTO.getActividad());

            asignacionService.save(asignacion);
            return ResponseEntity.ok("Asignación actualizada correctamente");
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        if (id != null) {
            asignacionService.deleteById(id);
            return ResponseEntity.ok("Asignación eliminada correctamente");
        }

        return ResponseEntity.notFound().build();
    }

}
