package com.btech.bitacora.model.dto;

import org.springframework.stereotype.Component;



@Component
public class ProjectDTO {

    public ProjectDTO() {
    }

    public ProjectDTO(String nombreProyecto, String cliente) {
        this.nombreProyecto = nombreProyecto;
        this.cliente = cliente;
    }
    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }



    private String nombreProyecto;

    private String cliente;

    @Override
    public String toString() {
        return "Proyecto{" +
                ", nombreProyecto='" + nombreProyecto + '\'' +
                ", cliente='" + cliente + '\'' +
                '}';
}


}
