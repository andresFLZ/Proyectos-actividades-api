package com.application.rest.controller;

import com.application.rest.controller.dto.ActividadDTO;
import com.application.rest.entities.Actividad;
import com.application.rest.entities.Proyecto;
import com.application.rest.service.IActividadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ActividadController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ActividadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IActividadService actividadService;

    private final String BASE_URL = "/api/actividad";

    private final ObjectMapper mapper = new ObjectMapper();

    private static Actividad actividad1;

    private static List<Actividad> actividades;

    private static ActividadDTO actividadDTO;

    private static Actividad actividadSave;

    @BeforeAll
    public static void setUp() {
        Proyecto proyecto = Proyecto.builder().id(1).build();

        actividad1 = Actividad.builder()
                .id(1)
                .nombre("Actividad 1")
                .descripcion("Descripción de actividad 1")
                .tiempoPlaneado(155)
                .tiempoEmpleado(155)
                .proyecto(proyecto)
                .build();

        Actividad actividad2 = Actividad.builder()
                .id(2)
                .nombre("Actividad 2")
                .descripcion("Descripción de actividad 2")
                .tiempoPlaneado(122)
                .tiempoEmpleado(100)
                .proyecto(proyecto)
                .build();

        Actividad actividad3 = Actividad.builder()
                .id(3)
                .nombre("Actividad 3")
                .descripcion("Descripción de actividad 3")
                .tiempoPlaneado(85)
                .tiempoEmpleado(90)
                .proyecto(proyecto)
                .build();

        actividades = List.of(actividad1, actividad2, actividad3);

        actividadDTO = ActividadDTO.builder()
                .nombre("Actividad 16")
                .descripcion("Descripción de actividad 16")
                .tiempoPlaneado(155)
                .tiempoEmpleado(155)
                .proyecto(proyecto)
                .build();

        actividadSave = Actividad.builder()
                .nombre(actividadDTO.getNombre())
                .descripcion(actividadDTO.getDescripcion())
                .tiempoPlaneado(actividadDTO.getTiempoPlaneado())
                .tiempoEmpleado(actividadDTO.getTiempoEmpleado())
                .proyecto(actividadDTO.getProyecto())
                .build();
    }

    @Test
    @Order(1)
    void shouldReturnAllActividades() throws Exception {
        //when
        when(actividadService.findAll()).thenReturn(actividades);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Verifica que el estado HTTP sea 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Verifica que el tipo de contenido sea JSON
                .andExpect(jsonPath("$.length()").value(3)) // Verifica que el JSON tenga 3 elementos
                .andExpect(jsonPath("$[0].nombre").value("Actividad 1")) // Verifica el nombre de la primera actividad
                .andExpect(jsonPath("$[0].descripcion").value("Descripción de actividad 1")) // Verifica la descripción de la primera actividad
                .andExpect(jsonPath("$[1].nombre").value("Actividad 2")) // Verifica el nombre de la segunda actividad
                .andExpect(jsonPath("$[2].nombre").value("Actividad 3")); // Verifica el nombre de la tercera actividad

        //then
        verify(actividadService, times(1)).findAll();
    }

    @Test
    @Order(2)
    void shouldReturnActividadById() throws Exception {
        //given
        Integer id = 1;

        //when
        when(actividadService.findById(1)).thenReturn(Optional.ofNullable(actividad1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Verifica que el estado HTTP sea 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Verifica que el tipo de contenido sea JSON
                .andExpect(jsonPath("$.id").value(id)) // Verifica que el ID de la actividad sea correcto
                .andExpect(jsonPath("$.nombre").value("Actividad 1")) // Verifica el nombre de la actividad
                .andExpect(jsonPath("$.descripcion").value("Descripción de actividad 1")) // Verifica la descripción
                .andExpect(jsonPath("$.tiempoPlaneado").value(155)) // Verifica el tiempo planeado
                .andExpect(jsonPath("$.tiempoEmpleado").value(155)); // Verifica el tiempo empleado

        //then
        verify(actividadService, times(1)).findById(1);
    }

    @Test
    @Order(3)
    void shouldSaveActividad() throws Exception {
        //when
        doNothing().when(actividadService).save(actividadSave);
        mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_URL)
                .content(mapper.writeValueAsString(actividadDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())  // Verifica que el estado HTTP sea 201 Created
            .andExpect(header().exists("Location"))  // Verifica que haya un encabezado 'Location'
            .andExpect(content().string(""))  // Verifica que no haya contenido en la respuesta
            .andExpect(header().string("Location", "/api/actividad"));

        // Then
        verify(actividadService).save(actividadSave);
    }

    @Test
    @Order(4)
    void shouldUpdateActividad() throws Exception {
        //given
        Integer id = 1;

        //when
        when(actividadService.findById(1)).thenReturn(Optional.ofNullable(actividad1));
        doNothing().when(actividadService).save(actividadSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(BASE_URL + "/" + id)
                        .content(mapper.writeValueAsString(actividadDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Verifica que el estado HTTP sea 200 OK
                .andExpect(content().string("Actividad actualizada exitosamente")); // Verifica el mensaje de éxito en la respuesta

        // Then
        verify(actividadService, times(1)).findById(id); // Verifica que findById fue llamado una vez
        verify(actividadService, times(1)).save(any(Actividad.class));
    }

    @Test
    @Order(5)
    void shouldDeleteActividad() throws Exception {
        //given
        Integer id = 1;

        //when
        doNothing().when(actividadService).deleteById(1);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(BASE_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Verifica que el estado HTTP es 200 OK
                .andExpect(content().string("Actividad eliminada exitosamente")); // Verifica el mensaje de éxito

        // Then
        verify(actividadService, times(1)).deleteById(id);

    }
}