package com.application.rest.controller;

import com.application.rest.controller.dto.ProyectoDTO;
import com.application.rest.entities.Proyecto;
import com.application.rest.service.IProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyecto")
public class ProyectoController {

    @Autowired
    IProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<ProyectoDTO> proyectoDTOList = proyectoService.findAll()
                .stream().map(proyecto -> ProyectoDTO.builder()
                        .id(proyecto.getId())
                        .nombre(proyecto.getNombre())
                        .descripcion(proyecto.getDescripcion())
                        .actividades(proyecto.getActividades())
                        .build()
                ).toList();

        return ResponseEntity.ok(proyectoDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Optional<Proyecto> proyectoOptional = proyectoService.findById(id);

        if (proyectoOptional.isPresent()) {
            Proyecto proyecto = proyectoOptional.get();

            ProyectoDTO proyectoDTO = ProyectoDTO.builder()
                    .id(proyecto.getId())
                    .nombre(proyecto.getNombre())
                    .descripcion(proyecto.getDescripcion())
                    .actividades(proyecto.getActividades())
                    .build();

            return ResponseEntity.ok(proyectoDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProyectoDTO proyectoDTO) throws URISyntaxException {
        if (proyectoDTO.getNombre().isBlank() || proyectoDTO.getDescripcion().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre y descripción no deben estar vacios");
        }

        proyectoService.save(Proyecto.builder()
                .nombre(proyectoDTO.getNombre())
                .descripcion(proyectoDTO.getDescripcion())
                .actividades(proyectoDTO.getActividades())
                .build());

        return ResponseEntity.created(new URI("/api/proyecto")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ProyectoDTO proyectoDTO) {
        Optional<Proyecto> proyectoOptional = proyectoService.findById(id);

        if (proyectoOptional.isPresent()) {
            Proyecto proyecto = proyectoOptional.get();

            proyecto.setNombre(proyectoDTO.getNombre());
            proyecto.setDescripcion(proyectoDTO.getDescripcion());
            proyecto.setActividades(proyectoDTO.getActividades());

            proyectoService.save(proyecto);

            return ResponseEntity.ok().body("Proyecto actualizado correctamente");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (id != null) {
            proyectoService.deleteById(id);

            return ResponseEntity.ok().body("Proyecto eliminado correctamente");
        }

        return ResponseEntity.notFound().build();
    }
}
