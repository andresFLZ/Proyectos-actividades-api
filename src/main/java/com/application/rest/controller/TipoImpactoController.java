package com.application.rest.controller;

import com.application.rest.controller.dto.TipoImpactoDTO;
import com.application.rest.entities.TipoImpacto;
import com.application.rest.service.ITipoImpactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipo-impacto")
public class TipoImpactoController {

    @Autowired
    ITipoImpactoService tipoImpactoService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<TipoImpactoDTO> tipoImpactoDTOList = tipoImpactoService.findAll()
                .stream().map(tipoImpacto -> TipoImpactoDTO.builder()
                        .id(tipoImpacto.getId())
                        .nombre(tipoImpacto.getNombre())
                        .descripcion(tipoImpacto.getDescripcion())
                        .inconvenientes(tipoImpacto.getInconvenientes())
                        .build()
                ).toList();

        return ResponseEntity.ok(tipoImpactoDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@RequestParam Integer id) {
        Optional<TipoImpacto> tipoImpactoOptional = tipoImpactoService.findById(id);

        if (tipoImpactoOptional.isPresent()) {
            TipoImpacto tipoImpacto = tipoImpactoOptional.get();

            TipoImpactoDTO tipoImpactoDTO = TipoImpactoDTO.builder()
                    .id(tipoImpacto.getId())
                    .nombre(tipoImpacto.getNombre())
                    .descripcion(tipoImpacto.getDescripcion())
                    .inconvenientes(tipoImpacto.getInconvenientes())
                    .build();

            return ResponseEntity.ok(tipoImpactoDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody TipoImpactoDTO tipoImpactoDTO) throws URISyntaxException {
        if (tipoImpactoDTO.getNombre().isBlank() || tipoImpactoDTO.getDescripcion().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre y descripción no deben ser nulos");
        }

        tipoImpactoService.save(TipoImpacto.builder()
                .nombre(tipoImpactoDTO.getNombre())
                .descripcion(tipoImpactoDTO.getDescripcion())
                .build()
        );

        return ResponseEntity.created(new URI("/api/tipo-impacto")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody TipoImpactoDTO tipoImpactoDTO) {
        Optional<TipoImpacto> tipoImpactoOptional = tipoImpactoService.findById(id);

        if (tipoImpactoOptional.isPresent()) {
            TipoImpacto tipoImpacto = tipoImpactoOptional.get();

            tipoImpacto.setNombre(tipoImpactoDTO.getNombre());
            tipoImpacto.setDescripcion(tipoImpactoDTO.getDescripcion());

            tipoImpactoService.save(tipoImpacto);

            return ResponseEntity.ok().body("Tipo de impacto actualizado correctamente");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (id != null) {
            tipoImpactoService.deleteById(id);

            return ResponseEntity.ok().body("Tipo de impacto eliminado correctamente");
        }

        return ResponseEntity.notFound().build();
    }
}
