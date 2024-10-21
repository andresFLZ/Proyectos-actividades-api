package com.application.rest.service.impl;

import com.application.rest.entities.TipoImpacto;
import com.application.rest.persistence.ITipoImpactoDAO;
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
class TipoImpactoServiceImplTest {

    @Mock
    private ITipoImpactoDAO tipoImpactoDAO;

    @InjectMocks
    private TipoImpactoServiceImpl tipoImpactoService;

    private static List<TipoImpacto> tipoImpactos;
    private static TipoImpacto tipoImpacto;

    @BeforeAll
    static void setUp() {
        tipoImpacto = TipoImpacto.builder()
                .id(1)
                .nombre("Bajo")
                .descripcion("Descripción del impacto 1")
                .build();

        TipoImpacto tipoImpacto2 = TipoImpacto.builder()
                .id(2)
                .nombre("Medio")
                .descripcion("Descripción del impacto 2")
                .build();

        TipoImpacto tipoImpacto3 = TipoImpacto.builder()
                .id(3)
                .nombre("Fuerte")
                .descripcion("Descripción del impacto 3")
                .build();

        tipoImpactos = List.of(tipoImpacto, tipoImpacto2, tipoImpacto3);
    }

    @Test
    void shouldReturnAllTipoImpactos() {
        //when
        when(tipoImpactoDAO.findAll()).thenReturn(tipoImpactos);
        List<TipoImpacto> tipoImpactosList = tipoImpactoService.findAll();

        //then
        assertNotNull(tipoImpactosList, "La lista no debería ser nula");
        assertEquals(3, tipoImpactosList.size(), "El tamaño de la lista debería ser 3");
        verify(tipoImpactoDAO, times(1)).findAll();
    }

    @Test
    void shouldReturnTipoImpactoById() {
        //given
        Integer id = 1;

        //when
        when(tipoImpactoDAO.findById(1)).thenReturn(Optional.ofNullable(tipoImpacto));
        Optional<TipoImpacto> tipoImpactoOptional = tipoImpactoService.findById(id);

        //then
        assertTrue(tipoImpactoOptional.isPresent(), "El tipo de impacto debería estar presente");
        assertEquals(id, tipoImpactoOptional.get().getId(), "El ID del tipo de impacto no coincide");
        assertEquals("Bajo", tipoImpactoOptional.get().getNombre(), "El nombre del tipo de impacto no coincide");
        assertEquals("Descripción del impacto 1", tipoImpactoOptional.get().getDescripcion(), "La descripción no coincide");
        verify(tipoImpactoDAO, times(1)).findById(id);
    }

    @Test
    void shouldSaveTipoImpacto() {
        //when
        tipoImpactoService.save(tipoImpacto);

        //then
        verify(tipoImpactoDAO, times(1)).save(tipoImpacto);
    }

    @Test
    void shouldDeleteTipoImpacto() {
        //given
        Integer id = 1;

        //when
        tipoImpactoService.deleteById(id);

        //then
        verify(tipoImpactoDAO, times(1)).deleteById(id);
    }
}