package com.btech.catalogo_micro.dto;

public class TipoRegistroDTO {

    private String nombre;

    public TipoRegistroDTO(String nombre) {
        this.nombre = nombre;
    }

    public TipoRegistroDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
