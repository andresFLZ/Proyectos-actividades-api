package com.application.rest.persistence.impl;

import com.application.rest.entities.Proyecto;
import com.application.rest.entities.Responsable;
import com.application.rest.persistence.IResponsableDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ResponsableDAOImplTest {

    @Autowired
    private IResponsableDAO responsableDAO;

    @Test
    void shouldReturnAllResponsables() {
        //when
        List<Responsable> responsables = responsableDAO.findAll();

        //then
        assertNotNull(responsables);
        assertFalse(responsables.isEmpty());
        assertEquals(20, responsables.size(), "El tamaño de la lista debería ser 15");
        assertEquals("Carlos López", responsables.get(0).getNombre());
        assertEquals("carlos.lopez@mail.com", responsables.get(0).getEmail());
    }

    @Test
    void shouldReturnResponsableById() {
        //given
        Integer id = 1;

        //when
        Optional<Responsable> responsable = responsableDAO.findById(id);

        //then
        assertTrue(responsable.isPresent());

        responsable.ifPresent(res -> assertEquals(id, res.getId()));

        responsable.ifPresent(res -> {
            assertEquals("Carlos López", res.getNombre());
            assertEquals("carlos.lopez@mail.com", res.getEmail());
        });
    }

    @Test
    void shouldSaveResponsable() {
        //given
        Responsable responsable = Responsable.builder()
                .nombre("Jairo Limas")
                .email("jairo.limas@mail.com")
                .build();

        //when
        responsableDAO.save(responsable);

        //then
        assertNotNull(responsable.getId());

        Optional<Responsable> responsableGuardado = responsableDAO.findById(responsable.getId());

        assertTrue(responsableGuardado.isPresent());

        // Verificar los valores de los atributos
        responsableGuardado.ifPresent(res -> {
            assertEquals("Jairo Limas", res.getNombre());
            assertEquals("jairo.limas@mail.com", res.getEmail());
        });
    }

    @Test
    void shouldDeleteResponsable() {
        //given
        Integer id = 1;

        Optional<Responsable> responsableAntesEliminar = responsableDAO.findById(id);
        assertTrue(responsableAntesEliminar.isPresent());

        //when
        responsableDAO.deleteById(id);

        //then
        Optional<Responsable> responsableEliminado = responsableDAO.findById(id);
        assertTrue(responsableEliminado.isEmpty());
    }
}