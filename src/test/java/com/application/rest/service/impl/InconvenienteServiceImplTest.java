package com.application.rest.service.impl;

import com.application.rest.entities.Actividad;
import com.application.rest.entities.Inconveniente;
import com.application.rest.entities.TipoImpacto;
import com.application.rest.persistence.IInconvenienteDAO;
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
class InconvenienteServiceImplTest {

    @Mock
    private IInconvenienteDAO inconvenienteDAO;

    @InjectMocks
    private  InconvenienteServiceImpl inconvenienteServiceImpl;

    private static List<Inconveniente> inconvenientes;
    private static Inconveniente inconveniente;

    @BeforeAll
    public static void setUp() {
        TipoImpacto tipoImpacto = TipoImpacto.builder().id(1).build();
        Actividad actividad = Actividad.builder().id(1).build();

        inconveniente = Inconveniente.builder()
                .id(1)
                .descripcion("Inconveniente 1")
                .tipoImpacto(tipoImpacto)
                .actividad(actividad)
                .build();

        Inconveniente inconveniente2 = Inconveniente.builder()
                .id(2)
                .descripcion("Inconveniente 2")
                .tipoImpacto(tipoImpacto)
                .actividad(actividad)
                .build();

        Inconveniente inconveniente3 = Inconveniente.builder()
                .id(3)
                .descripcion("Inconveniente 3")
                .tipoImpacto(tipoImpacto)
                .actividad(actividad)
                .build();

        inconvenientes = List.of(inconveniente, inconveniente2, inconveniente3);
    }

    @Test
    void shouldReturnAllInconvenientes() {
        //when
        when(inconvenienteDAO.findAll()).thenReturn(inconvenientes);
        List<Inconveniente> inconvenientesList = inconvenienteServiceImpl.findAll();

        //then
        assertNotNull(inconvenientesList, "La lista no debería ser nula");
        assertEquals(3, inconvenientesList.size(), "El tamaño de la lista debería ser 3");
        verify(inconvenienteDAO, times(1)).findAll();
    }

    @Test
    void shouldReturnInconvenienteById() {
        //given
        Integer id = 1;

        //when
        when(inconvenienteDAO.findById(1)).thenReturn(Optional.ofNullable(inconveniente));
        Optional<Inconveniente> inconvenienteOptional = inconvenienteServiceImpl.findById(id);

        //then
        assertTrue(inconvenienteOptional.isPresent(), "el inconveniente debería estar presente");
        assertEquals(id, inconvenienteOptional.get().getId(), "El ID del inconveniente no coincide");
        assertEquals("Inconveniente 1", inconvenienteOptional.get().getDescripcion(), "La descripción no coincide");
        verify(inconvenienteDAO, times(1)).findById(id);
    }

    @Test
    void shouldSaveInconveniente() {
        //when
        inconvenienteServiceImpl.save(inconveniente);

        //then
        verify(inconvenienteDAO, times(1)).save(inconveniente);
    }

    @Test
    void shouldDeleteInconveniente() {
        //given
        Integer id = 1;

        //when
        inconvenienteServiceImpl.deleteById(id);

        //then
        verify(inconvenienteDAO, times(1)).deleteById(id);
    }
}