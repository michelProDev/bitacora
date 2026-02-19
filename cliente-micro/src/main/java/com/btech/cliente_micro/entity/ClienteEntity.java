package com.btech.cliente_micro.entity;


import jakarta.persistence.*;


@Entity
@Table(name = "cliente")
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    @Column(name = "pais_id")
    private Long paisId;

    public ClienteEntity() {
    }

    public ClienteEntity(Long id, String nombre, Long paisId) {
        this.id = id;
        this.nombre = nombre;
        this.paisId = paisId;
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
}

