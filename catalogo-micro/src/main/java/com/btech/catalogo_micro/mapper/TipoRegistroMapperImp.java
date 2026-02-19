package com.btech.catalogo_micro.mapper;

import com.btech.catalogo_micro.dto.TipoRegistroDTO;
import com.btech.catalogo_micro.entity.TipoRegistroEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TipoRegistroMapperImp implements TipoRegistroMapper {

    @Override
    public TipoRegistroDTO toDTO(TipoRegistroEntity entity) {
        return entity == null ? new TipoRegistroDTO() : new TipoRegistroDTO(entity.getNombre());
    }

    @Override
    public TipoRegistroEntity toEntity(TipoRegistroDTO dto) {
        return dto == null ? new TipoRegistroEntity() : new TipoRegistroEntity(null, dto.getNombre());
    }

    @Override
    public List<TipoRegistroDTO> toListDTO(List<TipoRegistroEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TipoRegistroEntity> toListEntity(List<TipoRegistroDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
