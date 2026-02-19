package com.btech.account.mapper;

import com.btech.account.entities.ProyectosDTO;
import com.btech.account.entities.ProyectosEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProyectoMapperImp implements ProyectoMapper {



    @Override
    public ProyectosDTO proyectoToDTO (ProyectosEntity proyectosEntity) {
        return proyectosEntity == null ? new ProyectosDTO() : new ProyectosDTO(proyectosEntity.getId(), proyectosEntity.getIdCliente(), proyectosEntity.getNombreProyecto(), null);
    }

    @Override
    public ProyectosEntity proyectosDTOToEntity(ProyectosDTO proyectosDTO) {
        return proyectosDTO == null ? new ProyectosEntity() : new ProyectosEntity(null, proyectosDTO.getIdCliente(), proyectosDTO.getNombreProyecto());
    }

    @Override
    public List<ProyectosDTO> proyectoToListDTO(List<ProyectosEntity> proyectosEntities) {
        return proyectosEntities.stream()
                .map(this::proyectoToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProyectosEntity> ProyectosDTOToListEntity(List<ProyectosDTO> proyectosDTOS) {
        return proyectosDTOS.stream()
                .map(this::proyectosDTOToEntity)
                .collect(Collectors.toList());
    }
}
