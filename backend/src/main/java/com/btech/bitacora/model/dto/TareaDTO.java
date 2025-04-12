package com.btech.bitacora.model.dto;

import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TareaDTO {

    @NotNull(message = "El ID del desarrollador es obligatorio")
    @Positive(message = "El ID del desarrollador debe ser un número positivo")
    private Long idDesarrollador;

    @NotNull(message = "El ID del proyecto es obligatorio")
    @Positive(message = "El ID del proyecto debe ser un número positivo")
    private Long idProyecto;

    @NotNull(message = "La fecha de la tarea es obligatoria")
    @PastOrPresent(message = "La fecha de la tarea no puede ser futura")
    private LocalDate fechaTarea;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 5, max = 500, message = "La descripción debe tener entre 5 y 500 caracteres")
    private String descripcionTarea;

    public TareaDTO() {
    }

    public TareaDTO(Long idDesarrollador, Long idProyecto, LocalDate fechaTarea, String descripcionTarea) {
        this.idDesarrollador = idDesarrollador;
        this.idProyecto = idProyecto;
        this.fechaTarea = fechaTarea;
        this.descripcionTarea = descripcionTarea;
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