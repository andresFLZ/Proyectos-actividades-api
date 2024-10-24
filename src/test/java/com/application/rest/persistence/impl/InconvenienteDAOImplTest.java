package com.application.rest.persistence.impl;

import com.application.rest.entities.Actividad;
import com.application.rest.entities.Asignacion;
import com.application.rest.entities.Inconveniente;
import com.application.rest.entities.TipoImpacto;
import com.application.rest.persistence.IInconvenienteDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class InconvenienteDAOImplTest {

    @Autowired
    private IInconvenienteDAO inconvenienteDAO;

    @Test
    void shouldReturnAllInconvenientes() {
        //when
        List<Inconveniente> inconvenientes = inconvenienteDAO.findAll();

        //then
        assertNotNull(inconvenientes);
        assertFalse(inconvenientes.isEmpty());
        assertEquals(10, inconvenientes.size(), "El tamaño de la lista debería ser 15");
        assertEquals("Inconveniente 1", inconvenientes.get(0).getDescripcion());
        assertEquals(1, inconvenientes.get(0).getActividad().getId());
        assertEquals(1, inconvenientes.get(0).getTipoImpacto().getId());
    }

    @Test
    void shouldReturnInconvenienteById() {
        //given
        Integer id = 1;

        //when
        Optional<Inconveniente> inconveniente = inconvenienteDAO.findById(id);

        //then
        assertTrue(inconveniente.isPresent(), "El inconveniente debería estar presente");

        inconveniente.ifPresent(inc -> assertEquals(id, inc.getId(), "El ID del inconveniente debería ser " + id));

        inconveniente.ifPresent(inc -> {
            assertEquals(1, inc.getTipoImpacto().getId(), "El id del inconveniente debería ser 1");
            assertEquals(1, inc.getActividad().getId(), "El id del inconveniente debería ser 1");
        });
    }

    @Test
    void shouldSaveInconveniente() {
        //given
        Actividad actividad = Actividad.builder().id(1).build();
        TipoImpacto tipoImpacto = TipoImpacto.builder().id(1).build();

        Inconveniente inconveniente = Inconveniente.builder()
                .descripcion("Inconveniente 11")
                .tipoImpacto(tipoImpacto)
                .actividad(actividad)
                .build();

        //when
        inconvenienteDAO.save(inconveniente);

        //then
        assertNotNull(inconveniente.getId(), "El ID del inconveniente no debería ser nulo después de guardarla");

        Optional<Inconveniente> inconvenienteGuardado = inconvenienteDAO.findById(inconveniente.getId());

        assertTrue(inconvenienteGuardado.isPresent(), "El inconveniente guardado debería estar presente en la base de datos");

        // Verificar los valores de los atributos
        inconvenienteGuardado.ifPresent(inc -> {
            assertEquals("Inconveniente 11", inc.getDescripcion(), "La descripción del inconveniente debería ser Inconveniente 11");
            assertEquals(1, inc.getActividad().getId(), "El id de la actividad debería ser 1");
        });
    }

    @Test
    void shouldDeleteInconveniente() {
        //given
        Integer id = 1;

        Optional<Inconveniente> inconvenienteAntesEliminar = inconvenienteDAO.findById(id);
        assertTrue(inconvenienteAntesEliminar.isPresent(), "El inconveniente debería existir antes de eliminarlo");

        //when
        inconvenienteDAO.deleteById(id);

        //then
        Optional<Inconveniente> inconvenienteEliminado = inconvenienteDAO.findById(id);
        assertTrue(inconvenienteEliminado.isEmpty(), "El inconveniente no debería existir después de ser eliminado");
    }
}