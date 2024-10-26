package com.application.rest.controller;

import com.application.rest.controller.dto.ResponsableDTO;
import com.application.rest.entities.Responsable;
import com.application.rest.service.IResponsableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/responsable")
public class ResponsableController {

    @Autowired
    IResponsableService responsableService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<ResponsableDTO> responsableDTOList = responsableService.findAll()
                .stream().map(responsable -> ResponsableDTO.builder()
                        .id(responsable.getId())
                        .nombre(responsable.getNombre())
                        .email(responsable.getEmail())
                        .asignaciones(responsable.getAsignaciones())
                        .build()
                ).toList();

        return ResponseEntity.ok(responsableDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Optional<Responsable> responsableOptional = responsableService.findById(id);

        if (responsableOptional.isPresent()) {
            Responsable responsable = responsableOptional.get();

            ResponsableDTO responsableDTO = ResponsableDTO.builder()
                    .id(responsable.getId())
                    .nombre(responsable.getNombre())
                    .email(responsable.getEmail())
                    .asignaciones(responsable.getAsignaciones())
                    .build();

            return ResponseEntity.ok(responsableDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ResponsableDTO responsableDTO) throws URISyntaxException {
        if (responsableDTO.getNombre().isBlank() || responsableDTO.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre y el email son obligatorios");
        }

        responsableService.save(Responsable.builder()
                .nombre(responsableDTO.getNombre())
                .email(responsableDTO.getEmail())
                .build()
        );

        return ResponseEntity.created(new URI("/api/responsable")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ResponsableDTO responsableDTO) {
        Optional<Responsable> responsableOptional = responsableService.findById(id);

        if (responsableOptional.isPresent()) {
            Responsable responsable = responsableOptional.get();

            responsable.setNombre(responsableDTO.getNombre());
            responsable.setEmail(responsableDTO.getEmail());

            responsableService.save(responsable);

            return ResponseEntity.ok().body("Responsable actualizado con exito");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (id != null) {
            responsableService.deleteById(id);

            return ResponseEntity.ok().body("Responsable eliminado con exito");
        }

        return ResponseEntity.notFound().build();
    }
}
