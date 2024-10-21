package com.application.rest.service.impl;

import com.application.rest.entities.Proyecto;
import com.application.rest.persistence.IProyectoDAO;
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
class ProyectoServiceImplTest {

    @Mock
    private IProyectoDAO proyectoDAO;

    @InjectMocks
    private ProyectoServiceImpl proyectoService;

    private static List<Proyecto> proyectos;
    private static Proyecto proyecto;

    @BeforeAll
    public static void setUp() {
        proyecto = Proyecto.builder()
                .id(1)
                .nombre("Proyecto 1")
                .descripcion("Descripción del proyecto 1")
                .build();

        Proyecto proyecto2 = Proyecto.builder()
                .id(2)
                .nombre("Proyecto 2")
                .descripcion("Descripción del proyecto 2")
                .build();

        Proyecto proyecto3 = Proyecto.builder()
                .id(3)
                .nombre("Proyecto 3")
                .descripcion("Descripción del proyecto 3")
                .build();

        proyectos = List.of(proyecto, proyecto2, proyecto3);
    }

    @Test
    void shouldReturnAllProyectos() {
        //when
        when(proyectoDAO.findAll()).thenReturn(proyectos);
        List<Proyecto> proyectosList = proyectoService.findAll();

        //then
        assertNotNull(proyectosList, "La lista no debería ser nula");
        assertEquals(3, proyectosList.size(), "El tamaño de la lista debería ser 3");
        verify(proyectoDAO, times(1)).findAll();
    }

    @Test
    void shouldReturnProyectoById() {
        //given
        Integer id = 1;

        //when
        when(proyectoDAO.findById(1)).thenReturn(Optional.ofNullable(proyecto));
        Optional<Proyecto> proyectoOptional = proyectoService.findById(id);

        //then
        assertTrue(proyectoOptional.isPresent(), "el proyecto debería estar presente");
        assertEquals(id, proyectoOptional.get().getId(), "El ID del proyecto no coincide");
        assertEquals("Proyecto 1", proyectoOptional.get().getNombre(), "El nombre del proyecto no coincide");
        assertEquals("Descripción del proyecto 1", proyectoOptional.get().getDescripcion(), "La descripción no coincide");
        verify(proyectoDAO, times(1)).findById(id);
    }

    @Test
    void shouldSaveProyecto() {
        //when
        proyectoService.save(proyecto);

        //then
        verify(proyectoDAO, times(1)).save(proyecto);
    }

    @Test
    void shouldDeleteProyecto() {
        //given
        Integer id = 1;

        //when
        proyectoService.deleteById(id);

        //then
        verify(proyectoDAO, times(1)).deleteById(id);
    }
}