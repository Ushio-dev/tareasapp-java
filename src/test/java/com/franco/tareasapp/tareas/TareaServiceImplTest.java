package com.franco.tareasapp.tareas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.franco.tareasapp.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TareaServiceImplTest {

    @Mock
    private TareasRepository tareasRepository;

    // injectmocks se encarga de que se inyecten las dependencias mockeadas que necesita el objeto para el test
    @InjectMocks
    private TareaServiceImpl tareaService;

    private Tarea tarea;

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);  // esto no hace falta si pongo la anotacion de mockito en la clase
        tarea = new Tarea();
        tarea.setId(1);
        tarea.setNombreTarea("Estudiar Testing");
        tarea.setActive(true);
    }

    @Test
    void findAll() {
        when(tareasRepository.findAll()).thenReturn(Arrays.asList(tarea));
        Assertions.assertNotNull(tareaService.findAll());
    }

    @Test
    void findOneWithNull() {
        when(tareasRepository.findById(any())).thenReturn(Optional.of(tarea));
        Assertions.assertNotNull(tareaService.findOne(1L));
        Assertions.assertNotNull(tareaService.findOne(null));
    }

    @Test
    @DisplayName("Test para guardar una tarea")
    void testGuardarTarea() {
        // given
        //given(tareasRepository.findById(tarea.getId())).willReturn(Optional.empty());
        given(tareasRepository.save(tarea)).willReturn(tarea);
        // when
        Tarea tareaGuardada = tareaService.saveTarea(tarea);

        // then
        assertThat(tareaGuardada).isNotNull();
    }

    @Test
    @DisplayName("Test para guardar una tarea con Throw exception")
    void testGuardarTareaConThrowException() {
        // given
        given(tareasRepository.findByNombreTarea(tarea.getNombreTarea())).willReturn(Optional.of(tarea));
        // when
        assertThrows(ResourceNotFoundException.class, () -> {
            tareaService.saveTarea(tarea);
        });

        // then
        verify(tareasRepository, never()).save(any(Tarea.class));
    }

    @Test
    @DisplayName("Test para listar las tareas")
    void testListarTareas() {
        // given
        Tarea tarea1 = new Tarea(1L,"Pasear a Logan", false);
        given(tareasRepository.findAll()).willReturn(List.of(tarea, tarea1));

        // when
        List<Tarea> tareas = tareaService.findAll();

        // then
        assertThat(tareas).isNotNull();
        assertThat(tareas.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test para listar una lista vacia")
    void testListarColeccionTarasVacia() {
        // given
        Tarea tarea1 = new Tarea(1L,"Pasear a Logan", false);
        given(tareasRepository.findAll()).willReturn(Collections.emptyList());

        // when
        List<Tarea> listaTareas = tareaService.findAll();

        // then
        assertThat(listaTareas).isEmpty();
        assertThat(listaTareas.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("Test para obtener empleado por id")
    void testObtenerTareaPorId() {
        // given
        given(tareasRepository.findById(1L)).willReturn(Optional.of(tarea));
        // when
        Tarea tareaGuardada = tareaService.findOne(tarea.getId()).get();
        // then
        assertThat(tareaGuardada).isNotNull();
    }
}