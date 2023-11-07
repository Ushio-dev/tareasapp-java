package com.franco.tareasapp.tareas;

import com.franco.tareasapp.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaServiceImpl implements TareaService{

    private final TareasRepository tareasRepository;

    public TareaServiceImpl(TareasRepository tareasRepository) {
        this.tareasRepository = tareasRepository;
    }
    @Override
    public List<Tarea> findAll() {
        return tareasRepository.findAll();
    }

    @Override
    public Optional<Tarea> findOne(Long id) {
        return tareasRepository.findById(id);
    }

    @Override
    public Tarea saveTarea(Tarea nuevaTarea) {
        Optional<Tarea> tareaGuardada = tareasRepository.findByNombreTarea(nuevaTarea.getNombreTarea());

        if (tareaGuardada.isPresent()) {
            throw new ResourceNotFoundException("La tarea ya existe");
        }

        return tareasRepository.save(nuevaTarea);
    }

    @Override
    public Tarea actualizarTarea(Tarea tarea) {
        return tareasRepository.save(tarea);
    }


}
