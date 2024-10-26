package com.application.rest.controller;

import com.application.rest.controller.dto.InconvenienteDTO;
import com.application.rest.entities.Actividad;
import com.application.rest.entities.Asignacion;
import com.application.rest.entities.Inconveniente;
import com.application.rest.entities.TipoImpacto;
import com.application.rest.service.IInconvenienteService;
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

@WebMvcTest(InconvenienteController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InconvenienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IInconvenienteService inconvenienteService;

    private final String BASE_URL = "/api/inconveniente";

    private final ObjectMapper mapper = new ObjectMapper();

    private static Inconveniente inconveniente1;

    private static List<Inconveniente> inconvenientes;

    private static InconvenienteDTO inconvenienteDTO;

    private static Inconveniente inconvenienteSave;

    @BeforeAll
    public static void setUp() {
        TipoImpacto tipoImpacto = TipoImpacto.builder().id(1).build();
        Actividad actividad = Actividad.builder().id(1).build();

        inconveniente1 = Inconveniente.builder()
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

        inconvenientes = List.of(inconveniente1, inconveniente2, inconveniente3);

        inconvenienteDTO = InconvenienteDTO.builder()
                .descripcion("Inconveniente 11")
                .tipoImpacto(tipoImpacto)
                .actividad(actividad)
                .build();

        inconvenienteSave = Inconveniente.builder()
                .descripcion(inconvenienteDTO.getDescripcion())
                .tipoImpacto(inconvenienteDTO.getTipoImpacto())
                .actividad(inconvenienteDTO.getActividad())
                .build();
    }

    @Test
    @Order(1)
    void shouldReturnAllInconvenientes() throws Exception {
        //when
        when(inconvenienteService.findAll()).thenReturn(inconvenientes);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].descripcion").value("Inconveniente 1"))
                .andExpect(jsonPath("$[0].actividad.id").value(1));

        //then
        verify(inconvenienteService, times(1)).findAll();
    }

    @Test
    @Order(2)
    void shouldReturnInconvenienteById() throws Exception {
        //given
        Integer id = 1;

        //when
        when(inconvenienteService.findById(1)).thenReturn(Optional.ofNullable(inconveniente1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.descripcion").value("Inconveniente 1"))
                .andExpect(jsonPath("$.actividad.id").value(1));

        //then
        verify(inconvenienteService, times(1)).findById(1);
    }

    @Test
    @Order(3)
    void shouldSaveInconveniente() throws Exception {
        //when
        doNothing().when(inconvenienteService).save(inconvenienteSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL)
                        .content(mapper.writeValueAsString(inconvenienteDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(content().string(""))
                .andExpect(header().string("Location", "/api/inconveniente"));

        // Then
        verify(inconvenienteService).save(inconvenienteSave);
    }

    @Test
    @Order(4)
    void shouldUpdateInconveniente() throws Exception {
        //given
        Integer id = 1;

        //when
        when(inconvenienteService.findById(1)).thenReturn(Optional.ofNullable(inconveniente1));
        doNothing().when(inconvenienteService).save(inconvenienteSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(BASE_URL + "/" + id)
                        .content(mapper.writeValueAsString(inconvenienteDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Inconveniente actualizado correctamente"));;

        // Then
        verify(inconvenienteService, times(1)).findById(id);
        verify(inconvenienteService, times(1)).save(any(Inconveniente.class));
    }

    @Test
    @Order(5)
    void shouldDeleteInconveniente() throws Exception {
        //given
        Integer id = 1;

        //when
        doNothing().when(inconvenienteService).deleteById(1);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(BASE_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Inconveniente eliminado correctamente"));

        // Then
        verify(inconvenienteService, times(1)).deleteById(id);
    }
}