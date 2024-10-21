package com.application.rest.service.impl;

import com.application.rest.entities.Actividad;
import com.application.rest.entities.Proyecto;
import com.application.rest.persistence.IActividadDAO;
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
class ActividadServiceImplTest {

    @Mock
    private IActividadDAO actividadDAO;

    @InjectMocks
    private ActividadServiceImpl actividadServiceImpl;

    private static List<Actividad> actividades;
    private static Actividad actividad;

    @BeforeAll
    public static void setUp() {
        //given
        Proyecto proyecto = Proyecto.builder().id(1).build();

        actividad = Actividad.builder()
                .id(1)
                .nombre("Actividad 1")
                .descripcion("Descripción de actividad 1")
                .tiempoEmpleado(200)
                .tiempoPlaneado(200)
                .proyecto(proyecto)
                .build();

        Actividad actividad2 = Actividad.builder()
                .id(2)
                .nombre("Actividad 2")
                .descripcion("Descripción de actividad 2")
                .tiempoEmpleado(150)
                .tiempoPlaneado(100)
                .proyecto(proyecto)
                .build();

        Actividad actividad3 = Actividad.builder()
                .id(3)
                .nombre("Actividad 3")
                .descripcion("Descripción de actividad 3")
                .tiempoEmpleado(98)
                .tiempoPlaneado(105)
                .proyecto(proyecto)
                .build();

        actividades = List.of(actividad, actividad2, actividad3);
    }

    @Test
    void shouldReturnAllActividades() {
        //when
        when(actividadDAO.findAll()).thenReturn(actividades);
        List<Actividad> actividadList = actividadServiceImpl.findAll();

        //then
        assertNotNull(actividadList, "La lista no debería ser nula");
        assertEquals(3, actividadList.size(), "El tamaño de la lista debería ser 3");
        verify(actividadDAO, times(1)).findAll();
    }

    @Test
    void shouldReturnActividadById() {
        //given
        Integer id = 1;

        //when
        when(actividadDAO.findById(1)).thenReturn(Optional.ofNullable(actividad));
        Optional<Actividad> actividadOptional = actividadServiceImpl.findById(id);

        //then
        assertTrue(actividadOptional.isPresent(), "La actividad debería estar presente");
        assertEquals(id, actividadOptional.get().getId(), "El ID de la actividad no coincide");
        assertEquals("Actividad 1", actividadOptional.get().getNombre(), "El nombre de la actividad no coincide");
        assertEquals("Descripción de actividad 1", actividadOptional.get().getDescripcion(), "La descripción no coincide");
        verify(actividadDAO, times(1)).findById(id);
    }

    @Test
    void shouldSaveActividadSuccessfully() {
        //when
        actividadServiceImpl.save(actividad);

        //then
        verify(actividadDAO, times(1)).save(actividad);
    }

    @Test
    void shouldDeleteActividadSuccessfully() {
        //given
        Integer id = 1;

        //when
        actividadServiceImpl.deleteById(id);

        //then
        verify(actividadDAO, times(1)).deleteById(id);
    }
}