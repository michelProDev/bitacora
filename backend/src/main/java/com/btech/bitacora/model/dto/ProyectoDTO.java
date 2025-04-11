package com.btech.bitacora.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProyectoDTO {



    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El cliente es obligatorio")
    @Size(max = 100, message = "El nombre del cliente no puede exceder los 100 caracteres")
    private String cliente;

    // Constructores
    public ProyectoDTO() {
    }

    public ProyectoDTO(Long id, String nombre, String cliente) {

        this.nombre = nombre;
        this.cliente = cliente;
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