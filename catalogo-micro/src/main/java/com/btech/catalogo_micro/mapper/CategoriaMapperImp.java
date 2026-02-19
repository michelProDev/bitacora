package com.btech.catalogo_micro.mapper;

import com.btech.catalogo_micro.dto.CategoriaDTO;
import com.btech.catalogo_micro.entity.CategoriaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriaMapperImp implements CategoriaMapper {

    @Override
    public CategoriaDTO toDTO(CategoriaEntity entity) {
        return entity == null ? new CategoriaDTO() : new CategoriaDTO(entity.getNombre());
    }

    @Override
    public CategoriaEntity toEntity(CategoriaDTO dto) {
        return dto == null ? new CategoriaEntity() : new CategoriaEntity(null, dto.getNombre());
    }

    @Override
    public List<CategoriaDTO> toListDTO(List<CategoriaEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoriaEntity> toListEntity(List<CategoriaDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
