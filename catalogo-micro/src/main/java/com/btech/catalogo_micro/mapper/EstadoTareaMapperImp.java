package com.btech.catalogo_micro.mapper;

import com.btech.catalogo_micro.dto.EstadoTareaDTO;
import com.btech.catalogo_micro.entity.EstadoTareaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoTareaMapperImp implements EstadoTareaMapper {

    @Override
    public EstadoTareaDTO toDTO(EstadoTareaEntity entity) {
        return entity == null ? new EstadoTareaDTO() : new EstadoTareaDTO(entity.getNombre());
    }

    @Override
    public EstadoTareaEntity toEntity(EstadoTareaDTO dto) {
        return dto == null ? new EstadoTareaEntity() : new EstadoTareaEntity(null, dto.getNombre());
    }

    @Override
    public List<EstadoTareaDTO> toListDTO(List<EstadoTareaEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EstadoTareaEntity> toListEntity(List<EstadoTareaDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
