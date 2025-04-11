package com.btech.bitacora.model.mapper;

import com.btech.bitacora.model.dto.ProyectoDTO;
import com.btech.bitacora.model.entity.ProyectoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProyectoMapper {

    public  ProyectoDTO toDto(ProyectoEntity entity) {
        ProyectoDTO dto = new ProyectoDTO();
        dto.setNombre(entity.getNombre());
        dto.setCliente(entity.getCliente());
        return dto;
    }

    public  ProyectoEntity toEntity(ProyectoDTO dto) {
        ProyectoEntity entity = new ProyectoEntity();
        entity.setNombre(dto.getNombre());
        entity.setCliente(dto.getCliente());
        return entity;
    }

    public  void updateEntityFromDto(ProyectoDTO dto, ProyectoEntity entity) {
        entity.setNombre(dto.getNombre());
        entity.setCliente(dto.getCliente());
    }
}