package com.btech.bitacora.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ProjectDTO {
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

    public ProjectDTO() {
    }

    public ProjectDTO(String nombreProyecto, String cliente) {
        this.nombreProyecto = nombreProyecto;
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
