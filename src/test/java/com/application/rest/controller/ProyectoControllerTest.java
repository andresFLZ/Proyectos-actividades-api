package com.application.rest.controller;

import com.application.rest.controller.dto.ProyectoDTO;
import com.application.rest.entities.Inconveniente;
import com.application.rest.entities.Proyecto;
import com.application.rest.service.IProyectoService;
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

@WebMvcTest(ProyectoController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProyectoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProyectoService proyectoService;

    private final String BASE_URL = "/api/proyecto";

    private final ObjectMapper mapper = new ObjectMapper();

    private static Proyecto proyecto1;

    private static List<Proyecto> proyectos;

    private static ProyectoDTO proyectoDTO;

    private static Proyecto proyectoSave;

    @BeforeAll
    public static void setUp() {
        proyecto1 = Proyecto.builder()
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

        proyectos = List.of(proyecto1, proyecto2, proyecto3);

        proyectoDTO = ProyectoDTO.builder()
                .nombre("Proyecto 16")
                .descripcion("Descripción del proyecto 16")
                .build();

        proyectoSave = Proyecto.builder()
                .nombre(proyectoDTO.getNombre())
                .descripcion(proyectoDTO.getDescripcion())
                .build();
    }

    @Test
    @Order(1)
    void shouldReturnAllProyectos() throws Exception {
        //when
        when(proyectoService.findAll()).thenReturn(proyectos);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].descripcion").value("Descripción del proyecto 1"))
                .andExpect(jsonPath("$[0].nombre").value("Proyecto 1"));

        //then
        verify(proyectoService, times(1)).findAll();
    }

    @Test
    @Order(2)
    void shouldReturnProyectoById() throws Exception {
        //given
        Integer id = 1;

        //when
        when(proyectoService.findById(1)).thenReturn(Optional.ofNullable(proyecto1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.descripcion").value("Descripción del proyecto 1"))
                .andExpect(jsonPath("$.nombre").value("Proyecto 1"));

        //then
        verify(proyectoService, times(1)).findById(1);
    }

    @Test
    @Order(3)
    void shouldSaveProyecto() throws Exception {
        //when
        doNothing().when(proyectoService).save(proyectoSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL)
                        .content(mapper.writeValueAsString(proyectoDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(content().string(""))
                .andExpect(header().string("Location", "/api/proyecto"));

        // Then
        verify(proyectoService).save(proyectoSave);
    }

    @Test
    @Order(4)
    void shouldUpdateProyecto() throws Exception {
        //given
        Integer id = 1;

        //when
        when(proyectoService.findById(1)).thenReturn(Optional.ofNullable(proyecto1));
        doNothing().when(proyectoService).save(proyectoSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(BASE_URL + "/" + id)
                        .content(mapper.writeValueAsString(proyectoDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Proyecto actualizado correctamente"));;

        // Then
        verify(proyectoService, times(1)).findById(id);
        verify(proyectoService, times(1)).save(any(Proyecto.class));
    }

    @Test
    @Order(5)
    void shouldDeleteProyecto() throws Exception {
        //given
        Integer id = 1;

        //when
        doNothing().when(proyectoService).deleteById(1);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(BASE_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Proyecto eliminado correctamente"));

        // Then
        verify(proyectoService, times(1)).deleteById(id);
    }
}