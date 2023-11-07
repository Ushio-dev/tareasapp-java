package com.franco.tareasapp.tareas;

import java.util.List;
import java.util.Optional;

public interface TareaService {
    List<Tarea> findAll();
    Optional<Tarea> findOne(Long id);

    Tarea saveTarea(Tarea nuevaTarea);

    Tarea actualizarTarea(Tarea tarea);
}
