package com.franco.tareasapp.tareas;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TareasRepositoryTest {
    @Autowired
    private TareasRepository tareasRepository;

    private Tarea tarea;

    @BeforeEach
    void setUp() {
        tarea = new Tarea("Pasear a Logan", false);
    }


    @Test
    @DisplayName("guardar una tarea")
    void guardarTareaTest() {
        // given - configuracion previa o preconfiguracion
        Tarea tarea1 = new Tarea("Pasear a Logan", false);

        // when - condicion a probar
        Tarea tareaGuardada = tareasRepository.save(tarea1);

        //then - verificar la salida
        assertThat(tareaGuardada).isNotNull();
        assertThat(tareaGuardada.getId()).isGreaterThan(0);

    }

    @Test
    @DisplayName("Test para listar tareas")
    void testListarTareas() {
        // given
        Tarea tarea1 = new Tarea("Pasear a Logan", false);

        tareasRepository.save(tarea1);
        tareasRepository.save(tarea);

        // when
        List<Tarea> listaTareas = tareasRepository.findAll();

        // then
        assertThat(listaTareas).isNotNull();
        assertThat(listaTareas.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test para obtener tarea por id")
    void testObtenerTareaPorId() {
        // given
        tareasRepository.save(tarea);

        // when - comportamiento a accion que vamos a probar
        Tarea tarea1 = tareasRepository.findById(tarea.getId()).get();

        // then
        assertThat(tarea1).isNotNull();
    }

    @Test
    @DisplayName("Test para actualizar una tarea")
    void testActualizarTarea() {
        tareasRepository.save(tarea);

        // when
        Tarea tareaGuardada = tareasRepository.findById(tarea.getId()).get();
        tareaGuardada.setNombreTarea("Comprar Pan");

        Tarea tareaActualizada = tareasRepository.save(tareaGuardada);

        // then
        assertThat(tareaActualizada.getNombreTarea()).isEqualTo("Comprar Pan");
    }

    @Test
    @DisplayName("Test para elminar un tarea")
    void testEliminarTarea() {
        tareasRepository.save(tarea);

        // when
        tareasRepository.deleteById(tarea.getId());

        Optional<Tarea> tareaOptional = tareasRepository.findById(tarea.getId());

        assertThat(tareaOptional).isEmpty();
    }

    @Test
    @DisplayName("Test para buscar por nombre de tarea")
    void testFindByTaskName() {
        tareasRepository.save(tarea);
        Tarea tareaGuardada = tareasRepository.findByNombreTarea(tarea.getNombreTarea()).get();

        assertThat(tareaGuardada).isNotNull();
    }
}