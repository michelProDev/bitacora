package com.btech.hr.dto;

import java.util.Date;

public class RegistroDTO {

    private Long id;
    private Date fecha;
    private Long recurso;
    private Long proyecto;
    private Long horas;
    private String descripcion;
    private Long tipoRegistroId;
    private String tipoRegistroNombre;
    private Long estadoTareaId;
    private String estadoTareaNombre;
    private String recursoNombre;
    private String proyectoNombre;

    public RegistroDTO(Long id, Date fecha, Long recurso, Long proyecto, Long horas, String descripcion, Long tipoRegistroId,
                       String tipoRegistroNombre, Long estadoTareaId, String estadoTareaNombre,
                       String recursoNombre, String proyectoNombre) {
        this.id = id;
        this.fecha = fecha;
        this.recurso = recurso;
        this.proyecto = proyecto;
        this.horas = horas;
        this.descripcion = descripcion;
        this.tipoRegistroId = tipoRegistroId;
        this.tipoRegistroNombre = tipoRegistroNombre;
        this.estadoTareaId = estadoTareaId;
        this.estadoTareaNombre = estadoTareaNombre;
        this.recursoNombre = recursoNombre;
        this.proyectoNombre = proyectoNombre;
    }

    public RegistroDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getRecurso() {
        return recurso;
    }

    public void setRecurso(Long recurso) {
        this.recurso = recurso;
    }

    public Long getProyecto() {
        return proyecto;
    }

    public void setProyecto(Long proyecto) {
        this.proyecto = proyecto;
    }

    public Long getHoras() {
        return horas;
    }

    public void setHoras(Long horas) {
        this.horas = horas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getTipoRegistroId() {
        return tipoRegistroId;
    }

    public void setTipoRegistroId(Long tipoRegistroId) {
        this.tipoRegistroId = tipoRegistroId;
    }

    public String getTipoRegistroNombre() {
        return tipoRegistroNombre;
    }

    public void setTipoRegistroNombre(String tipoRegistroNombre) {
        this.tipoRegistroNombre = tipoRegistroNombre;
    }

    public Long getEstadoTareaId() {
        return estadoTareaId;
    }

    public void setEstadoTareaId(Long estadoTareaId) {
        this.estadoTareaId = estadoTareaId;
    }

    public String getEstadoTareaNombre() {
        return estadoTareaNombre;
    }

    public void setEstadoTareaNombre(String estadoTareaNombre) {
        this.estadoTareaNombre = estadoTareaNombre;
    }

    public String getRecursoNombre() {
        return recursoNombre;
    }

    public void setRecursoNombre(String recursoNombre) {
        this.recursoNombre = recursoNombre;
    }

    public String getProyectoNombre() {
        return proyectoNombre;
    }

    public void setProyectoNombre(String proyectoNombre) {
        this.proyectoNombre = proyectoNombre;
    }

}
