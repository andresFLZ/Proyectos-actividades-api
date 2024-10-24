package com.application.rest.persistence.impl;

import com.application.rest.entities.Actividad;
import com.application.rest.entities.Asignacion;
import com.application.rest.entities.Responsable;
import com.application.rest.persistence.IAsignacionDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AsignacionDAOImplTest {

    @Autowired
    private IAsignacionDAO asignacionDAO;

    @Test
    void shouldReturnAllAsignaciones() {
        //when
        List<Asignacion> asignaciones = asignacionDAO.findAll();

        //then
        assertNotNull(asignaciones);
        assertFalse(asignaciones.isEmpty());
        assertEquals(10, asignaciones.size(), "El tamaño de la lista debería ser 15");
        assertEquals(1, asignaciones.get(0).getResponsable().getId());
        assertEquals(1, asignaciones.get(0).getActividad().getId());
    }

    @Test
    void shouldReturnAsignacionById() {
        //given
        Integer id = 1;

        //when
        Optional<Asignacion> asignacion = asignacionDAO.findById(id);

        //then
        assertTrue(asignacion.isPresent(), "La asignación debería estar presente");

        asignacion.ifPresent(asig -> assertEquals(id, asig.getId(), "El ID de la asignación debería ser " + id));

        asignacion.ifPresent(asig -> {
            assertEquals(1, asig.getResponsable().getId(), "El id de la actividad debería ser 1");
            assertEquals(1, asig.getActividad().getId(), "El id del responsable debería ser 1");
        });
    }

    @Test
    void shouldSaveAsignacion() {
        //given
        Responsable responsable = Responsable.builder().id(1).build();
            Actividad actividad = Actividad.builder().id(1).build();

        Asignacion asignacion = Asignacion.builder()
                .responsable(responsable)
                .actividad(actividad)
                .build();

        //when
        asignacionDAO.save(asignacion);

        //then
        assertNotNull(asignacion.getId(), "El ID de la asignación no debería ser nulo después de guardarla");

        Optional<Asignacion> asignacionGuardada = asignacionDAO.findById(asignacion.getId());

        assertTrue(asignacionGuardada.isPresent(), "La asignación guardada debería estar presente en la base de datos");

        // Verificar los valores de los atributos
        asignacionGuardada.ifPresent(asig -> {
            assertEquals(1, asig.getResponsable().getId(), "El id del responsable debería ser 1");
            assertEquals(1, asig.getActividad().getId(), "El id de la actividad debería ser 1");
        });
    }

    @Test
    void shouldDeleteAsignacion() {
        //given
        Integer id = 1;

        Optional<Asignacion> asignacionAntesEliminar = asignacionDAO.findById(id);
        assertTrue(asignacionAntesEliminar.isPresent(), "La asignación debería existir antes de eliminarla");

        //when
        asignacionDAO.deleteById(id);

        //then
        Optional<Asignacion> asignacionEliminada = asignacionDAO.findById(id);
        assertTrue(asignacionEliminada.isEmpty(), "La asignación no debería existir después de ser eliminada");
    }
}