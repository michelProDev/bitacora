package com.btech.catalogo_micro.mapper;

import com.btech.catalogo_micro.dto.PaisDTO;
import com.btech.catalogo_micro.entity.PaisEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaisMapper {

    PaisDTO toDTO(PaisEntity entity);

    PaisEntity toEntity(PaisDTO dto);

    List<PaisDTO> toListDTO(List<PaisEntity> entities);

    List<PaisEntity> toListEntity(List<PaisDTO> dtos);
}
