package com.application.rest.controller;

import com.application.rest.controller.dto.TipoImpactoDTO;
import com.application.rest.entities.Inconveniente;
import com.application.rest.entities.TipoImpacto;
import com.application.rest.service.ITipoImpactoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TipoImpactoController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TipoImpactoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITipoImpactoService tipoImpactoService;

    private final String BASE_URL = "/api/tipo-impacto";

    private final ObjectMapper mapper = new ObjectMapper();

    private static TipoImpacto tipoImpacto1;

    private static List<TipoImpacto> tipoImpactos;

    private static TipoImpactoDTO tipoImpactoDTO;

    private static TipoImpacto tipoImpactoSave;

    @BeforeAll
    public static void setUp() {
        tipoImpacto1 = TipoImpacto.builder()
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

        tipoImpactos = List.of(tipoImpacto1, tipoImpacto2, tipoImpacto3);

        tipoImpactoDTO = TipoImpactoDTO.builder()
                .nombre("Exagerado")
                .descripcion("Descripción del impacto 4")
                .build();

        tipoImpactoSave = TipoImpacto.builder()
                .nombre(tipoImpactoDTO.getNombre())
                .descripcion(tipoImpactoDTO.getDescripcion())
                .build();
    }

    @Test
    @Order(1)
    void shouldReturnAllTipoImpactos() throws Exception {
        //when
        when(tipoImpactoService.findAll()).thenReturn(tipoImpactos);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].descripcion").value("Descripción del impacto 1"))
                .andExpect(jsonPath("$[0].nombre").value("Bajo"));

        //then
        verify(tipoImpactoService, times(1)).findAll();
    }

    @Test
    @Order(2)
    void shouldReturnTipoImpactoById() throws Exception {
        //given
        Integer id = 1;

        //when
        when(tipoImpactoService.findById(1)).thenReturn(Optional.ofNullable(tipoImpacto1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.descripcion").value("Descripción del impacto 1"))
                .andExpect(jsonPath("$.nombre").value("Bajo"));

        //then
        verify(tipoImpactoService, times(1)).findById(1);
    }

    @Test
    @Order(3)
    void shouldSaveTipoImpacto() throws Exception {
        //when
        doNothing().when(tipoImpactoService).save(tipoImpactoSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL)
                        .content(mapper.writeValueAsString(tipoImpactoDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(content().string(""))
                .andExpect(header().string("Location", "/api/tipo-impacto"));

        // Then
        verify(tipoImpactoService).save(tipoImpactoSave);
    }

    @Test
    @Order(4)
    void shouldUpdateTipoImpacto() throws Exception {
        //given
        Integer id = 1;

        //when
        when(tipoImpactoService.findById(1)).thenReturn(Optional.ofNullable(tipoImpacto1));
        doNothing().when(tipoImpactoService).save(tipoImpactoSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(BASE_URL + "/" + id)
                        .content(mapper.writeValueAsString(tipoImpactoDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Tipo de impacto actualizado correctamente"));;

        // Then
        verify(tipoImpactoService, times(1)).findById(id);
        verify(tipoImpactoService, times(1)).save(any(TipoImpacto.class));
    }

    @Test
    @Order(5)
    void shouldDeleteTipoImpacto() throws Exception {
        //given
        Integer id = 1;

        //when
        doNothing().when(tipoImpactoService).deleteById(1);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(BASE_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Tipo de impacto eliminado correctamente"));

        // Then
        verify(tipoImpactoService, times(1)).deleteById(id);
    }
}