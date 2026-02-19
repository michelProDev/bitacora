package com.btech.catalogo_micro.mapper;

import com.btech.catalogo_micro.dto.TipoRegistroDTO;
import com.btech.catalogo_micro.entity.TipoRegistroEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TipoRegistroMapper {

    TipoRegistroDTO toDTO(TipoRegistroEntity entity);

    TipoRegistroEntity toEntity(TipoRegistroDTO dto);

    List<TipoRegistroDTO> toListDTO(List<TipoRegistroEntity> entities);

    List<TipoRegistroEntity> toListEntity(List<TipoRegistroDTO> dtos);
}
