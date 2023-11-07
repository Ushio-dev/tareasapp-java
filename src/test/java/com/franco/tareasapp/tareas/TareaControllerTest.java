package com.franco.tareasapp.tareas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TareaService tareaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGuardarTarea() throws Exception {
        // given
        Tarea tarea = new Tarea(1L, "Pasear Logan", false);

        given(tareaService.saveTarea(any(Tarea.class))).willAnswer((invocation) -> invocation.getArgument(0));
        // when
        ResultActions response = mockMvc.perform(post("/api/tareas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tarea)));

        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreTarea", is(tarea.getNombreTarea())));
    }

    @Test
    void testGuardarTareaConNull() throws Exception {
        // given
        Tarea tarea = new Tarea(1L, "Pasear Logan", false);

        given(tareaService.saveTarea(any(Tarea.class))).willAnswer((invocation) -> invocation.getArgument(0));
        // when
        ResultActions response = mockMvc.perform(post("/api/tareas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)));

        // then
        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void listarTodasLasTareasTest() throws Exception {
        // given
        List<Tarea> tareas = new ArrayList<>();

        tareas.add(new Tarea(1L,"Pasear a Logan", false));
        tareas.add(new Tarea(2L,"Comprar pan", false));
        tareas.add(new Tarea(3L,"Estudiar testing", false));

        given(tareaService.findAll()).willReturn(tareas);
        // when
        ResultActions result = mockMvc.perform(get("/api/tareas"));
        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(tareas.size())));
    }

    @Test
    void testObtenerPorId() throws Exception {
        // given
        long tareaId = 1L;
        Tarea tarea1 = new Tarea(tareaId, "Pasear a Logan", false);
        given(tareaService.findOne(tareaId)).willReturn(Optional.of(tarea1));
        // when
        ResultActions response = mockMvc.perform(get("/api/tareas/{id}", tareaId));
        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombreTarea", is(tarea1.getNombreTarea())));
    }

    @Test
    void testObtenerTareaNoEncontrada() throws Exception {
        // given
        long tareaId = 1L;
        Tarea tarea1 = new Tarea(tareaId, "Pasear a Logan", false);
        given(tareaService.findOne(tareaId)).willReturn(Optional.empty());
        // when
        ResultActions response = mockMvc.perform(get("/api/tareas/{id}", tareaId));
        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testActualizarTarea() throws Exception {
        // given
        long tareaId = 1L;
        Tarea tareaGuardada = new Tarea(1L, "Pasear Logan", false);
        Tarea tareaActualizada = new Tarea(1L, "Comprar pan", false);

        given(tareaService.findOne(tareaId)).willReturn(Optional.of(tareaGuardada));
        given(tareaService.actualizarTarea(any(Tarea.class))).willAnswer((invocation) -> invocation.getArgument(0));
        // when
        ResultActions result = mockMvc.perform(put("/api/tareas/{id}", tareaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tareaActualizada)));
        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nombreTarea", is(tareaActualizada.getNombreTarea())));
    }
}