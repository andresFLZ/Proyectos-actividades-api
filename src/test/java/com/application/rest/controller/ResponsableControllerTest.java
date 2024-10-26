package com.application.rest.controller;

import com.application.rest.controller.dto.ResponsableDTO;
import com.application.rest.entities.Inconveniente;
import com.application.rest.entities.Responsable;
import com.application.rest.service.IResponsableService;
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

@WebMvcTest(ResponsableController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ResponsableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IResponsableService responsableService;

    private final String BASE_URL = "/api/responsable";

    private final ObjectMapper mapper = new ObjectMapper();

    private static Responsable responsable1;

    private static List<Responsable> responsables;

    private static ResponsableDTO responsableDTO;

    private static Responsable responsableSave;

    @BeforeAll
    public static void setUp() {
        responsable1 = Responsable.builder()
                .id(1)
                .nombre("Luis Martínez")
                .email("luis.martinez@mail.com")
                .build();

        Responsable responsable2 = Responsable.builder()
                .id(2)
                .nombre("Carlos López")
                .email("carlos.lopez@mail.com")
                .build();

        Responsable responsable3 = Responsable.builder()
                .id(3)
                .nombre("Ana Gómez")
                .email("ana.gomez@mail.com")
                .build();

        responsables = List.of(responsable1, responsable2, responsable3);

        responsableDTO = ResponsableDTO.builder()
                .nombre("Jairo Limas")
                .email("jairo.limas@mail.com")
                .build();

        responsableSave = Responsable.builder()
                .nombre(responsableDTO.getNombre())
                .email(responsableDTO.getEmail())
                .build();
    }

    @Test
    @Order(1)
    void shouldReturnAllResponsables() throws Exception {
        //when
        when(responsableService.findAll()).thenReturn(responsables);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].nombre").value("Luis Martínez"))
                .andExpect(jsonPath("$[0].email").value("luis.martinez@mail.com"));

        //then
        verify(responsableService, times(1)).findAll();
    }

    @Test
    @Order(2)
    void shouldReturnResponsableById() throws Exception {
        //given
        Integer id = 1;

        //when
        when(responsableService.findById(1)).thenReturn(Optional.ofNullable(responsable1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("Luis Martínez"))
                .andExpect(jsonPath("$.email").value("luis.martinez@mail.com"));

        //then
        verify(responsableService, times(1)).findById(1);
    }

    @Test
    @Order(3)
    void shouldSaveResponsable() throws Exception {
        //when
        doNothing().when(responsableService).save(responsableSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL)
                        .content(mapper.writeValueAsString(responsableDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(content().string(""))
                .andExpect(header().string("Location", "/api/responsable"));

        // Then
        verify(responsableService).save(responsableSave);
    }

    @Test
    @Order(4)
    void shouldUpdateResponsable() throws Exception {
        //given
        Integer id = 1;

        //when
        when(responsableService.findById(1)).thenReturn(Optional.ofNullable(responsable1));
        doNothing().when(responsableService).save(responsableSave);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(BASE_URL + "/" + id)
                        .content(mapper.writeValueAsString(responsableDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Responsable actualizado con exito"));;

        // Then
        verify(responsableService, times(1)).findById(id);
        verify(responsableService, times(1)).save(any(Responsable.class));
    }

    @Test
    void shouldDeleteResponsable() throws Exception {
        //given
        Integer id = 1;

        //when
        doNothing().when(responsableService).deleteById(1);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(BASE_URL + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Responsable eliminado con exito"));

        // Then
        verify(responsableService, times(1)).deleteById(id);
    }
}