package com.btech.bitacora.model.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "desarrolladores")
public class DesarrolladorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "pais", nullable = false, length = 50)
    private String pais;

    @Column(name = "categoria", nullable = false, length = 30)
    private String categoria;

    // Constructores
    public DesarrolladorEntity() {
    }

    public DesarrolladorEntity(String nombre, String pais, String categoria) {
        this.nombre = nombre;
        this.pais = pais;
        this.categoria = categoria;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


}