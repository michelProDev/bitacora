package com.btech.bitacora.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class DesarrolladorDTO {
    @NotBlank(message = "El nombre del desarrollador es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El país es obligatorio")
    @Size(max = 50, message = "El país no puede exceder los 50 caracteres")
    private String pais;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 30, message = "La categoría no puede exceder los 30 caracteres")
    private String categoria;

    public DesarrolladorDTO() {
    }

    public DesarrolladorDTO(String nombre, String pais, String categoria) {
        this.nombre = nombre;
        this.pais = pais;
        this.categoria = categoria;
    }

    public @NotBlank(message = "El nombre del desarrollador es obligatorio") @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "El nombre del desarrollador es obligatorio") @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres") String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank(message = "El país es obligatorio") @Size(max = 50, message = "El país no puede exceder los 50 caracteres") String getPais() {
        return pais;
    }

    public void setPais(@NotBlank(message = "El país es obligatorio") @Size(max = 50, message = "El país no puede exceder los 50 caracteres") String pais) {
        this.pais = pais;
    }

    public @NotBlank(message = "La categoría es obligatoria") @Size(max = 30, message = "La categoría no puede exceder los 30 caracteres") String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NotBlank(message = "La categoría es obligatoria") @Size(max = 30, message = "La categoría no puede exceder los 30 caracteres") String categoria) {
        this.categoria = categoria;
    }
}
