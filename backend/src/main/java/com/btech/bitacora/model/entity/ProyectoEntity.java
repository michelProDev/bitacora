package com.btech.bitacora.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "proyectos")
public class ProyectoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 55)
    private String nombre;

    @Column(name = "cliente", nullable = false, length = 55)
    private String cliente;

    // Constructores
    public ProyectoEntity() {
    }

    public ProyectoEntity(String nombre, String cliente) {
        this.nombre = nombre;
        this.cliente = cliente;
    }

    // Getters y Setters
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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

 }