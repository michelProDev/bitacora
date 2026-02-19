package com.btech.catalogo_micro.dto;

public class EstadoTareaDTO {

    private String nombre;

    public EstadoTareaDTO(String nombre) {
        this.nombre = nombre;
    }

    public EstadoTareaDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
