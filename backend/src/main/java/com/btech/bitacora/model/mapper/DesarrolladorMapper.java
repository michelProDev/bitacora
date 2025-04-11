package com.btech.bitacora.model.mapper;

import com.btech.bitacora.model.dto.DesarrolladorDTO;
import com.btech.bitacora.model.entity.DesarrolladorEntity;
import org.springframework.stereotype.Component;

@Component
public class DesarrolladorMapper {

    public  DesarrolladorDTO toDto(DesarrolladorEntity entity) {
        DesarrolladorDTO dto = new DesarrolladorDTO();
        dto.setNombre(entity.getNombre());
        dto.setPais(entity.getPais());
        dto.setCategoria(entity.getCategoria());
        return dto;
    }

    public  DesarrolladorEntity toEntity(DesarrolladorDTO dto) {
        DesarrolladorEntity entity = new DesarrolladorEntity();
        entity.setNombre(dto.getNombre());
        entity.setPais(dto.getPais());
        entity.setCategoria(dto.getCategoria());
        return entity;
    }

    public  void updateEntityFromDto(DesarrolladorDTO dto, DesarrolladorEntity entity) {
        entity.setNombre(dto.getNombre());
        entity.setPais(dto.getPais());
        entity.setCategoria(dto.getCategoria());
    }
}