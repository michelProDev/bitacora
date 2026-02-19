package com.btech.hr.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recursos")
public class RecursoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", unique = true)
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "categoria_id")
    private Long categoriaId;
    @Column(name = "tecnologia")
    private String tecnologia;
    @Column(name = "correo", unique = true)
    private String correo;
    @Column(name = "password")
    private String password;
    @Column(name = "rol")
    private String rol;

    public RecursoEntity(Long id, String nombre, String apellidos, Long categoriaId, String tecnologia,
                         String correo, String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.categoriaId = categoriaId;
        this.tecnologia = tecnologia;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

    public RecursoEntity() {
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

