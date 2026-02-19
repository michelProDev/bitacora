package com.btech.catalogo_micro.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_registro")
public class TipoRegistroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;

    public TipoRegistroEntity() {
    }

    public TipoRegistroEntity(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}
