package com.application.rest.persistence.impl;

import com.application.rest.entities.Asignacion;
import com.application.rest.entities.Proyecto;
import com.application.rest.persistence.IProyectoDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProyectoDAOImplTest {

    @Autowired
    private IProyectoDAO proyectoDAO;

    @Test
    void shouldReturnAllProyectos() {
        //when
        List<Proyecto> proyectos = proyectoDAO.findAll();

        //then
        assertNotNull(proyectos);
        assertFalse(proyectos.isEmpty());
        assertEquals(15, proyectos.size(), "El tamaño de la lista debería ser 15");
        assertEquals("Proyecto 1", proyectos.get(0).getNombre());
        assertEquals("Descripción del proyecto 1", proyectos.get(0).getDescripcion());
    }

    @Test
    void shouldReturnProyectoById() {
        //given
        Integer id = 1;

        //when
        Optional<Proyecto> proyecto = proyectoDAO.findById(id);

        //then
        assertTrue(proyecto.isPresent(), "El proyecto debería estar presente");

        proyecto.ifPresent(proy -> assertEquals(id, proy.getId(), "El ID del proyecto debería ser " + id));

        proyecto.ifPresent(proy -> {
            assertEquals("Proyecto 1", proy.getNombre(), "El nombre del proyecto debería ser Proyecto 1");
            assertEquals("Descripción del proyecto 1", proy.getDescripcion(), "La descripción del proyecto debería ser Descripción del proyecto 1");
        });
    }

    @Test
    void shouldSaveProyecto() {
        //given
        Proyecto proyecto = Proyecto.builder()
                .nombre("Proyecto 16")
                .descripcion("Descripción del proyecto 16")
                .build();

        //when
        proyectoDAO.save(proyecto);

        //then
        assertNotNull(proyecto.getId(), "El ID del proyecto no debería ser nulo después de guardarlo");

        Optional<Proyecto> proyectoGuardado = proyectoDAO.findById(proyecto.getId());

        assertTrue(proyectoGuardado.isPresent(), "El proyecto guardado debería estar presente en la base de datos");

        // Verificar los valores de los atributos
        proyectoGuardado.ifPresent(proy -> {
            assertEquals("Proyecto 16", proy.getNombre());
            assertEquals("Descripción del proyecto 16", proy.getDescripcion());
        });
    }

    @Test
    void shouldDeleteProyecto() {
        //given
        Integer id = 1;

        Optional<Proyecto> proyectoAntesEliminar = proyectoDAO.findById(id);
        assertTrue(proyectoAntesEliminar.isPresent());

        //when
        proyectoDAO.deleteById(id);

        //then
        Optional<Proyecto> proyectoEliminado = proyectoDAO.findById(id);
        assertTrue(proyectoEliminado.isEmpty());
    }
}