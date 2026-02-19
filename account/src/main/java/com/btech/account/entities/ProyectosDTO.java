package com.btech.account.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ProyectosDTO {

    private Long id;
    private Long idCliente;
    private String nombreProyecto;
    private String clienteNombre;

    public ProyectosDTO(Long id, Long idCliente, String nombreProyecto, String clienteNombre) {
        this.id = id;
        this.idCliente = idCliente;
        this.nombreProyecto = nombreProyecto;
        this.clienteNombre = clienteNombre;
    }

    public ProyectosDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }
}
