package com.application.rest.service.impl;

import com.application.rest.entities.Responsable;
import com.application.rest.persistence.IResponsableDAO;
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
class ResponsableServiceImplTest {

    @Mock
    private IResponsableDAO responsableDAO;

    @InjectMocks
    private  ResponsableServiceImpl responsableService;

    private static List<Responsable> responsables;
    private static Responsable responsable;

    @BeforeAll
    static void setUp() {
        responsable = Responsable.builder()
                .id(1)
                .nombre("Carlos López")
                .email("carlos.lopez@mail.com")
                .build();

        Responsable responsable2 = Responsable.builder()
                .id(2)
                .nombre("Ana Gómez")
                .email("ana.gomez@mail.com")
                .build();

        Responsable responsable3 = Responsable.builder()
                .id(3)
                .nombre("Luis Martínez")
                .email("luis.martinez@mail.com")
                .build();

        responsables = List.of(responsable, responsable2, responsable3);
    }

    @Test
    void shouldReturnAllResponsables() {
        //when
        when(responsableDAO.findAll()).thenReturn(responsables);
        List<Responsable> responsablesList = responsableService.findAll();

        //then
        assertNotNull(responsablesList, "La lista no debería ser nula");
        assertEquals(3, responsablesList.size(), "El tamaño de la lista debería ser 3");
        verify(responsableDAO, times(1)).findAll();
    }

    @Test
    void shouldReturnResponsableById() {
        //given
        Integer id = 1;

        //when
        when(responsableDAO.findById(1)).thenReturn(Optional.ofNullable(responsable));
        Optional<Responsable> responsableOptional = responsableService.findById(id);

        //then
        assertTrue(responsableOptional.isPresent(), "El responsable debería estar presente");
        assertEquals(id, responsableOptional.get().getId(), "El ID del responsable no coincide");
        assertEquals("Carlos López", responsableOptional.get().getNombre(), "El nombre del responsable no coincide");
        verify(responsableDAO, times(1)).findById(id);
    }

    @Test
    void shouldSaveResponsable() {
        //when
        responsableService.save(responsable);

        //then
        verify(responsableDAO, times(1)).save(responsable);
    }

    @Test
    void shouldDeleteResponsable() {
        //given
        Integer id = 1;

        //when
        responsableService.deleteById(id);

        //then
        verify(responsableDAO, times(1)).deleteById(id);
    }
}