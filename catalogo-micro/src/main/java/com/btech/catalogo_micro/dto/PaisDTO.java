package com.btech.catalogo_micro.dto;

public class PaisDTO {

    private String nombre;

    public PaisDTO(String nombre) {
        this.nombre = nombre;
    }

    public PaisDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
