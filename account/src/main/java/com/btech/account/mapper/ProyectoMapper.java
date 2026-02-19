package com.btech.account.mapper;

import com.btech.account.entities.ProyectosDTO;
import com.btech.account.entities.ProyectosEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProyectoMapper {

    ProyectosDTO proyectoToDTO(ProyectosEntity proyectosEntity);

    ProyectosEntity proyectosDTOToEntity(ProyectosDTO proyectosDTO);

    List<ProyectosDTO> proyectoToListDTO (List<ProyectosEntity> proyectosEntities);

    List<ProyectosEntity> ProyectosDTOToListEntity (List<ProyectosDTO> proyectosDTOS);
}
