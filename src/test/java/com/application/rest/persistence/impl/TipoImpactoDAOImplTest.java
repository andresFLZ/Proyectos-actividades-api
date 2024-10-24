package com.application.rest.persistence.impl;

import com.application.rest.entities.Responsable;
import com.application.rest.entities.TipoImpacto;
import com.application.rest.persistence.ITipoImpactoDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TipoImpactoDAOImplTest {

    @Autowired
    private ITipoImpactoDAO tipoImpactoDAO;

    @Test
    void shouldReturnAllTipoImpactos() {
        //when
        List<TipoImpacto> tipoImpactos = tipoImpactoDAO.findAll();

        //then
        assertNotNull(tipoImpactos);
        assertFalse(tipoImpactos.isEmpty());
        assertEquals(3, tipoImpactos.size(), "El tamaño de la lista debería ser 3");
        assertEquals("Bajo", tipoImpactos.get(0).getNombre());
        assertEquals("Descripción del impacto 1", tipoImpactos.get(0).getDescripcion());
    }

    @Test
    void shouldReturnTipoImpactoById() {
        //given
        Integer id = 1;

        //when
        Optional<TipoImpacto> tipoImpacto = tipoImpactoDAO.findById(id);

        //then
        assertTrue(tipoImpacto.isPresent());

        tipoImpacto.ifPresent(tipo -> assertEquals(id, tipo.getId()));

        tipoImpacto.ifPresent(tipo -> {
            assertEquals("Bajo", tipo.getNombre());
            assertEquals("Descripción del impacto 1", tipo.getDescripcion());
        });
    }

    @Test
    void shouldSaveTipoImpacto() {
        //given
        TipoImpacto tipoImpacto = TipoImpacto.builder()
                .nombre("Extremo")
                .descripcion("Descripción del impacto 4")
                .build();

        //when
        tipoImpactoDAO.save(tipoImpacto);

        //then
        assertNotNull(tipoImpacto.getId());

        Optional<TipoImpacto> tipoImpactoGuardado = tipoImpactoDAO.findById(tipoImpacto.getId());

        assertTrue(tipoImpactoGuardado.isPresent());

        // Verificar los valores de los atributos
        tipoImpactoGuardado.ifPresent(tipo -> {
            assertEquals("Extremo", tipo.getNombre());
            assertEquals("Descripción del impacto 4", tipo.getDescripcion());
        });
    }

    @Test
    void shouldDeleteTipoImpacto() {
        //given
        Integer id = 1;

        Optional<TipoImpacto> tipoImpactoAntesEliminar = tipoImpactoDAO.findById(id);
        assertTrue(tipoImpactoAntesEliminar.isPresent());

        //when
        tipoImpactoDAO.deleteById(id);

        //then
        Optional<TipoImpacto> tipoImpactoEliminado = tipoImpactoDAO.findById(id);
        assertTrue(tipoImpactoEliminado.isEmpty());
    }
}