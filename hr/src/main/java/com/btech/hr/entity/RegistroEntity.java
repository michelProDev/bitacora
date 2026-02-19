package com.btech.hr.entity;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "registro")
public class RegistroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "recurso")
    private Long recurso;

    @Column(name = "proyecto")
    private Long proyecto;

    @Column(name = "horas")
    private Long horas;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tipo_registro_id")
    private Long tipoRegistroId;

    @Column(name = "estado_tarea_id")
    private Long estadoTareaId;

    public RegistroEntity() {
    }

    public RegistroEntity(Long id, Date fecha, Long recurso, Long proyecto, Long horas, String descripcion, Long tipoRegistroId,
                          Long estadoTareaId) {
        this.id = id;
        this.fecha = fecha;
        this.recurso = recurso;
        this.proyecto = proyecto;
        this.horas = horas;
        this.descripcion = descripcion;
        this.tipoRegistroId = tipoRegistroId;
        this.estadoTareaId = estadoTareaId;
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

    public Long getEstadoTareaId() {
        return estadoTareaId;
    }

    public void setEstadoTareaId(Long estadoTareaId) {
        this.estadoTareaId = estadoTareaId;
    }

}

