package com.btech.hr.mapper;

import com.btech.hr.dto.RecursoDTO;
import com.btech.hr.entity.RecursoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RecursoMapper {

    RecursoDTO recursoToDTO(RecursoEntity recursoEntity);

    RecursoEntity recursoDTOToEntity(RecursoDTO recursoDTO);

    List<RecursoDTO> recursoToListDTO(List<RecursoEntity> recursoEntities);

    List<RecursoEntity> recursoDTOToListEntity(List<RecursoDTO> recursoDTOs);
}
