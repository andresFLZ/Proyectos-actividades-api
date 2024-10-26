package com.application.rest.controller;

import com.application.rest.controller.dto.AsignacionDTO;
import com.application.rest.entities.Actividad;
import com.application.rest.entities.Asignacion;
import com.application.rest.entities.Responsable;
import com.application.rest.service.IAsignacionService;
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

@WebMvcTest(AsignacionController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AsignacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAsignacionService asignacionService;

    private final String BASE_URL = "/api/asignacion";

    private final ObjectMapper mapper = new ObjectMapper();

    private static Asignacion asignacion1;

    private static List<Asignacion> asignaciones;

    private static AsignacionDTO asignacionDTO;

    private static Asignacion asignacionSave;

    @BeforeAll
    public static void setUp() {
        Responsable responsable = Responsable.builder().id(1).build();
        Actividad actividad = Actividad.builder().id(1).build();

        asignacion1 = Asignacion.builder()
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

        asignaciones = List.of(asignacion1, asignacion2, asignacion3);

        asignacionDTO = AsignacionDTO.builder()
                .responsable(responsable)
                .actividad(actividad)
                .build();

        asignacionSave = Asignacion.builder()
                .actividad(asignacionDTO.getActividad())
                .responsable(asignacionDTO.getResponsable())
                .build();
    }

    @Test
    @Order(1)
    void shouldReturnAllAsignaciones() throws Exception {
        //when
        when(asignacionService.findAll()).thenReturn(asignaciones);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].responsable.id").value(1))
                .andExpect(jsonPath("$[0].actividad.id").value(1));

        //then
        verify(asignacionService, times(1)).findAll();
    }

    @Test
    @Order(2)
    void shouldReturnAsignacionById() throws Exception {
        //given
        Integer id = 1;

        //when
        when(asignacionService.findById(1)).thenReturn(Optional.ofNullable(asignacion1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.responsable.id").value(1))
                .andExpect(jsonPath("$.actividad.id").value(1));

        //then
        verify(asignacionService, times(1)).findById(1);
    }

    @Test
    @Order(3)
    void shouldSaveAsignacion() throws Exception {
        //when
        doNothing().when(asignacionService).save(asignacionSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL)
                        .content(mapper.writeValueAsString(asignacionDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(content().string(""))
                .andExpect(header().string("Location", "/api/asignacion"));

        // Then
        verify(asignacionService).save(asignacionSave);
    }

    @Test
    @Order(4)
    void shouldUpdateAsignacion() throws Exception {
        //given
        Integer id = 1;

        //when
        when(asignacionService.findById(1)).thenReturn(Optional.ofNullable(asignacion1));
        doNothing().when(asignacionService).save(asignacionSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(BASE_URL + "/" + id)
                        .content(mapper.writeValueAsString(asignacionDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Then
        verify(asignacionService, times(1)).findById(id);
        verify(asignacionService, times(1)).save(any(Asignacion.class));
    }

    @Test
    @Order(5)
    void shouldDeleteAsignacion() throws Exception {
        //given
        Integer id = 1;

        //when
        doNothing().when(asignacionService).deleteById(1);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(BASE_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Asignacion eliminada correctamente"));

        // Then
        verify(asignacionService, times(1)).deleteById(id);
    }
}