package com.btech.hr.dto;

public class RecursoDTO {

    private Long id;
    private String nombre;
    private String apellidos;
    private Long categoriaId;
    private String categoriaNombre;
    private String tecnologia;
    private String correo;
    private String password;
    private String rol;

    public RecursoDTO() {
    }

    public RecursoDTO(Long id, String nombre, String apellidos, Long categoriaId, String categoriaNombre, String tecnologia,
                     String correo, String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
        this.tecnologia = tecnologia;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
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

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
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
