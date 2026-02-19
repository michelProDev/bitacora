package com.btech.cliente_micro.dto;

public class ClienteDTO {

    private Long id;
    private String nombre;
    private Long paisId;
    private String paisNombre;

    public ClienteDTO(Long id, String nombre, Long paisId, String paisNombre) {
        this.id = id;
        this.nombre = nombre;
        this.paisId = paisId;
        this.paisNombre = paisNombre;
    }

    public ClienteDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPaisId() {
        return paisId;
    }

    public void setPaisId(Long paisId) {
        this.paisId = paisId;
    }

    public String getPaisNombre() {
        return paisNombre;
    }

    public void setPaisNombre(String paisNombre) {
        this.paisNombre = paisNombre;
    }
}
