package com.application.rest.controller;

import com.application.rest.controller.dto.InconvenienteDTO;
import com.application.rest.entities.Inconveniente;
import com.application.rest.service.IInconvenienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inconveniente")
public class InconvenienteController {

    @Autowired
    IInconvenienteService inconvenienteService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<InconvenienteDTO> inconvenienteDTOList = inconvenienteService.findAll()
                .stream().map(inconveniente -> InconvenienteDTO.builder()
                        .id(inconveniente.getId())
                        .descripcion(inconveniente.getDescripcion())
                        .tipoImpacto(inconveniente.getTipoImpacto())
                        .actividad(inconveniente.getActividad())
                        .build()
                ).toList();

        return ResponseEntity.ok(inconvenienteDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Optional<Inconveniente> inconvenienteOptional = inconvenienteService.findById(id);

        if (inconvenienteOptional.isPresent()) {
            Inconveniente inconveniente = inconvenienteOptional.get();

            InconvenienteDTO inconvenienteDTO = InconvenienteDTO.builder()
                    .id(inconveniente.getId())
                    .descripcion(inconveniente.getDescripcion())
                    .tipoImpacto(inconveniente.getTipoImpacto())
                    .actividad(inconveniente.getActividad())
                    .build();

            return ResponseEntity.ok(inconvenienteDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody InconvenienteDTO inconvenienteDTO) throws URISyntaxException {
        if (inconvenienteDTO.getDescripcion().isBlank()) {
            return ResponseEntity.badRequest().body("La descripción no puede estar vacía");
        }

        inconvenienteService.save(Inconveniente.builder()
                .descripcion(inconvenienteDTO.getDescripcion())
                .tipoImpacto(inconvenienteDTO.getTipoImpacto())
                .actividad(inconvenienteDTO.getActividad())
                .build()
        );

        return ResponseEntity.created(new URI("/api/inconveniente")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody InconvenienteDTO inconvenienteDTO) {
        Optional<Inconveniente> inconvenienteOptional = inconvenienteService.findById(id);

        if (inconvenienteOptional.isPresent()) {
            Inconveniente inconveniente = inconvenienteOptional.get();

            inconveniente.setDescripcion(inconvenienteDTO.getDescripcion());
            inconveniente.setTipoImpacto(inconvenienteDTO.getTipoImpacto());
            inconveniente.setActividad(inconvenienteDTO.getActividad());

            inconvenienteService.save(inconveniente);

            return ResponseEntity.ok("Inconveniente actualizado correctamente");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (id != null) {
            inconvenienteService.deleteById(id);

            return ResponseEntity.ok("Inconveniente eliminado correctamente");
        }

        return ResponseEntity.notFound().build();
    }

}
