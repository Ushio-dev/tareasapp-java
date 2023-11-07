package com.franco.tareasapp.tareas;

import jakarta.persistence.*;

@Entity
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TASK_NAME")
    private String nombreTarea;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    public Tarea() {
    }

    public Tarea(String nombreTarea, boolean isActive) {
        this.nombreTarea = nombreTarea;
        this.isActive = isActive;
    }

    public Tarea(long id, String nombreTarea, boolean isActive) {
        this.id = id;
        this.nombreTarea = nombreTarea;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreTarea() {
        return nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
