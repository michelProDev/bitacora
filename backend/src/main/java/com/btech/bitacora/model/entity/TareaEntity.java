package com.btech.bitacora.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tareas")
public class TareaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_desarrollador", nullable = false)
    private Long idDesarrollador;

    @Column(name = "id_proyecto", nullable = false)
    private Long idProyecto;

    @Column(name = "fecha_tarea", nullable = false)
    private LocalDate fechaTarea;

    @Column(name = "descripcion_tarea", nullable = false, length = 500)
    private String descripcionTarea;


    public TareaEntity() {
    }

    public TareaEntity(Long idDesarrollador, Long idProyecto, LocalDate fechaTarea, String descripcionTarea) {
        this.idDesarrollador = idDesarrollador;
        this.idProyecto = idProyecto;
        this.fechaTarea = fechaTarea;
        this.descripcionTarea = descripcionTarea;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdDesarrollador() {
        return idDesarrollador;
    }

    public void setIdDesarrollador(Long idDesarrollador) {
        this.idDesarrollador = idDesarrollador;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public LocalDate getFechaTarea() {
        return fechaTarea;
    }

    public void setFechaTarea(LocalDate fechaTarea) {
        this.fechaTarea = fechaTarea;
    }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
    }


}