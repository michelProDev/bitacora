package com.btech.bitacora.model.mapper;


import com.btech.bitacora.model.dto.ProjectDTO;
import com.btech.bitacora.model.entities.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectDTO toDto(Project proyecto) {
        return new ProjectDTO(
                proyecto.getNombreProyecto(),
                proyecto.getCliente()
        );
    }

    public Project toEntity(ProjectDTO proyectoDTO) {
        Project proyecto = new Project();


        proyecto.setNombreProyecto(proyectoDTO.get());
        proyecto.setCliente(proyectoDTO.getCliente());
        return proyecto;
    }
}