package com.application.rest.persistence.impl;

import com.application.rest.entities.Actividad;
import com.application.rest.entities.Proyecto;
import com.application.rest.persistence.IActividadDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // Carga el contexto completo de Spring Boot para las pruebas
@Transactional
class ActividadDAOImplTest {

    @Autowired
    private IActividadDAO actividadDAO;

    @Test
    void shouldReturnAllActividades() {
        //when
        List<Actividad> actividades = actividadDAO.findAll();

        //then
        assertNotNull(actividades);
        assertFalse(actividades.isEmpty());
        assertEquals(15, actividades.size(), "El tamaño de la lista debería ser 15");
        assertEquals("Actividad 1", actividades.get(0).getNombre());
    }

    @Test
    void shouldReturnActividadById() {
        //given
        Integer id = 1;

        //when
        Optional<Actividad> actividad = actividadDAO.findById(id);

        //then
        // Verificar que la actividad existe
        assertTrue(actividad.isPresent(), "La actividad debería estar presente");

        // Verificar el ID de la actividad (si se encuentra)
        actividad.ifPresent(act -> assertEquals(id, act.getId(), "El ID de la actividad debería ser " + id));

        // Verificar otros atributos de la actividad
        actividad.ifPresent(act -> {
            assertEquals("Actividad 1", act.getNombre(), "El nombre de la actividad debería ser 'Actividad 1'");
            assertEquals("Descripción de actividad 1", act.getDescripcion(), "La descripción debería ser 'Descripción de actividad 1'");
        });
    }

    @Test
    void shouldSaveActividad() {
        //given
        Proyecto proyecto = Proyecto.builder().id(1).build();

        Actividad actividad = Actividad.builder()
                .nombre("Actividad 16")
                .descripcion("Descripción de actividad 16")
                .tiempoPlaneado(89)
                .tiempoEmpleado(97)
                .proyecto(proyecto)
                .build();

        //when
        actividadDAO.save(actividad);

        // then
        // Verificar que la actividad tiene un ID (lo que indica que se guardó)
        assertNotNull(actividad.getId(), "El ID de la actividad no debería ser nulo después de guardarla");

        // Puedes recuperar la actividad desde la base de datos para verificar los valores guardados
        Optional<Actividad> actividadGuardada = actividadDAO.findById(actividad.getId());

        assertTrue(actividadGuardada.isPresent(), "La actividad guardada debería estar presente en la base de datos");

        // Verificar los valores de los atributos
        actividadGuardada.ifPresent(act -> {
            assertEquals("Actividad 16", act.getNombre(), "El nombre de la actividad guardada debería ser 'Actividad 16'");
            assertEquals("Descripción de actividad 16", act.getDescripcion(), "La descripción de la actividad guardada debería ser 'Descripción de actividad 16'");
            assertEquals(89, act.getTiempoPlaneado(), "El tiempo planeado debería ser 89");
            assertEquals(97, act.getTiempoEmpleado(), "El tiempo empleado debería ser 97");
            assertEquals(1, act.getProyecto().getId(), "El proyecto de la actividad debería tener ID 1");
        });
    }

    @Test
    void shoulDeleteActividad() {
        //given
        Integer id = 1;

        // Verificar que la actividad existe antes de la eliminación
        Optional<Actividad> actividadAntesDeEliminar = actividadDAO.findById(id);
        assertTrue(actividadAntesDeEliminar.isPresent(), "La actividad debería existir antes de eliminarla");

        //when
        actividadDAO.deleteById(id);

        // then
        // Verificar que la actividad ha sido eliminada
        Optional<Actividad> actividadEliminada = actividadDAO.findById(id);
        assertTrue(actividadEliminada.isEmpty(), "La actividad no debería existir después de ser eliminada");
    }
}