package com.franco.tareasapp.tareas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TareasRepository extends JpaRepository<Tarea, Long> {
    Optional<Tarea> findByNombreTarea(String nombreTarea);
}
