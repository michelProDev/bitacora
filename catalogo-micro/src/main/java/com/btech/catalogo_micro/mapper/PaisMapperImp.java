package com.btech.catalogo_micro.mapper;

import com.btech.catalogo_micro.dto.PaisDTO;
import com.btech.catalogo_micro.entity.PaisEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaisMapperImp implements PaisMapper {

    @Override
    public PaisDTO toDTO(PaisEntity entity) {
        return entity == null ? new PaisDTO() : new PaisDTO(entity.getNombre());
    }

    @Override
    public PaisEntity toEntity(PaisDTO dto) {
        return dto == null ? new PaisEntity() : new PaisEntity(null, dto.getNombre());
    }

    @Override
    public List<PaisDTO> toListDTO(List<PaisEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaisEntity> toListEntity(List<PaisDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
