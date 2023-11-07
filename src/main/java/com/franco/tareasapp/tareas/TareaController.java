package com.franco.tareasapp.tareas;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // esto es nuevo
    public Tarea guardarTarea(@RequestBody Tarea nuevaTarea) {
        return tareaService.saveTarea(nuevaTarea);
    }

    @GetMapping
    public List<Tarea> listarTareas() {
        return tareaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerPorId(@PathVariable("id") long id) {
        Optional<Tarea> tareaOptional = tareaService.findOne(id);

        return tareaOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable("id") Long id, @RequestBody Tarea tarea) {
        return tareaService.findOne(id)
                .map(tareaGuardada -> {
                    tareaGuardada.setId(tarea.getId());
                    tareaGuardada.setNombreTarea(tarea.getNombreTarea());
                    tareaGuardada.setActive(tarea.isActive());

                    Tarea tareaActualizada = tareaService.actualizarTarea(tareaGuardada);

                    return new ResponseEntity<>(tareaActualizada, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
