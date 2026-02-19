package com.btech.catalogo_micro.mapper;

import com.btech.catalogo_micro.dto.EstadoTareaDTO;
import com.btech.catalogo_micro.entity.EstadoTareaEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EstadoTareaMapper {

    EstadoTareaDTO toDTO(EstadoTareaEntity entity);

    EstadoTareaEntity toEntity(EstadoTareaDTO dto);

    List<EstadoTareaDTO> toListDTO(List<EstadoTareaEntity> entities);

    List<EstadoTareaEntity> toListEntity(List<EstadoTareaDTO> dtos);
}
