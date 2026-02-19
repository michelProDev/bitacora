package com.btech.account.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "proyectos")
public class ProyectosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "idCLiente")
    private Long idCliente;
    @Column(name = "nombreProyecto", unique = true)
    private String nombreProyecto;

    public ProyectosEntity(Long id, Long idCliente, String nombreProyecto) {
        this.id = id;
        this.idCliente = idCliente;
        this.nombreProyecto = nombreProyecto;
    }

    public ProyectosEntity() {
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
}
