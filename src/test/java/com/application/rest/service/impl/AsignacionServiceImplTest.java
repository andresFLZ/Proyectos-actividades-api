package com.application.rest.service.impl;

import com.application.rest.entities.Actividad;
import com.application.rest.entities.Asignacion;
import com.application.rest.entities.Responsable;
import com.application.rest.persistence.IAsignacionDAO;
import com.application.rest.service.IAsignacionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsignacionServiceImplTest {

    @Mock
    private IAsignacionDAO asignacionDAO;

    @InjectMocks
    private AsignacionServiceImpl asignacionServiceImpl;

    private static List<Asignacion> asignaciones;
    private static Asignacion asignacion;

    @BeforeAll
    public static void setUpClass() {
        Responsable responsable = Responsable.builder().id(1).build();
        Actividad actividad = Actividad.builder().id(1).build();

        asignacion = Asignacion.builder()
                .id(1)
                .responsable(responsable)
                .actividad(actividad)
                .build();

        Asignacion asignacion2 = Asignacion.builder()
                .id(2)
                .responsable(responsable)
                .actividad(actividad)
                .build();

        Asignacion asignacion3 = Asignacion.builder()
                .id(3)
                .responsable(responsable)
                .actividad(actividad)
                .build();

        asignaciones = List.of(asignacion, asignacion2, asignacion3);
    }

    @Test
    void shouldReturnAllAsignaciones() {
        //when
        when(asignacionDAO.findAll()).thenReturn(asignaciones);
        List<Asignacion> asignacionList = asignacionServiceImpl.findAll();

        //then
        assertNotNull(asignacionList, "La lista no debería ser nula");
        assertEquals(3, asignacionList.size(), "El tamaño de la lista debería ser 3");
        verify(asignacionDAO, times(1)).findAll();
    }

    @Test
    void shouldReturnAsignacionById() {
        //given
        Integer id = 1;

        //when
        when(asignacionDAO.findById(1)).thenReturn(Optional.ofNullable(asignacion));
        Optional<Asignacion> asignacionById = asignacionServiceImpl.findById(id);

        //then
        assertTrue(asignacionById.isPresent(), "La asignación debería estar presente");
        assertEquals(id, asignacionById.get().getId(), "El ID de la asignación no coincide");
        verify(asignacionDAO, times(1)).findById(id);
    }

    @Test
    void shouldSaveAsignacionSuccessfully() {
        //when
        asignacionServiceImpl.save(asignacion);

        //then
        verify(asignacionDAO, times(1)).save(asignacion);
    }

    @Test
    void shouldDeleteAsignacionSuccessfully() {
        //given
        Integer id = 1;

        //when
        asignacionServiceImpl.deleteById(id);

        //then
        verify(asignacionDAO, times(1)).deleteById(id);
    }
}