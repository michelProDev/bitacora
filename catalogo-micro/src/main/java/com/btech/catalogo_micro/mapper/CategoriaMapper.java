package com.btech.catalogo_micro.mapper;

import com.btech.catalogo_micro.dto.CategoriaDTO;
import com.btech.catalogo_micro.entity.CategoriaEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaMapper {

    CategoriaDTO toDTO(CategoriaEntity entity);

    CategoriaEntity toEntity(CategoriaDTO dto);

    List<CategoriaDTO> toListDTO(List<CategoriaEntity> entities);

    List<CategoriaEntity> toListEntity(List<CategoriaDTO> dtos);
}
