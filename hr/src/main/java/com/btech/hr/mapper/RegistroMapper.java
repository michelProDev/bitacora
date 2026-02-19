package com.btech.hr.mapper;

import com.btech.hr.dto.RegistroDTO;
import com.btech.hr.entity.RegistroEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegistroMapper {

    RegistroDTO registroToDTO(RegistroEntity registroEntity);

    RegistroEntity registroDTOToEntity(RegistroDTO registroDTO);

    List<RegistroDTO> registroToListDTO(List<RegistroEntity> registroEntities);

    List<RegistroEntity> registroDTOToListEntity(List<RegistroDTO> registroDTOs);
}
