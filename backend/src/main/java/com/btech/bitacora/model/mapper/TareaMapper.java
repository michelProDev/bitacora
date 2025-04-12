package com.btech.bitacora.model.mapper;

import com.btech.bitacora.model.dto.TareaDTO;
import com.btech.bitacora.model.entity.TareaEntity;
import org.springframework.stereotype.Component;


import org.springframework.stereotype.Component;

@Component
public class TareaMapper {

    public TareaDTO toDto(TareaEntity entity) {
        TareaDTO dto = new TareaDTO();
        dto.setIdDesarrollador(entity.getIdDesarrollador());
        dto.setIdProyecto(entity.getIdProyecto());
        dto.setFechaTarea(entity.getFechaTarea());
        dto.setDescripcionTarea(entity.getDescripcionTarea());
        return dto;
    }


    public TareaEntity toEntity(TareaDTO dto) {
        TareaEntity entity = new TareaEntity();
        entity.setIdDesarrollador(dto.getIdDesarrollador());
        entity.setIdProyecto(dto.getIdProyecto());
        entity.setFechaTarea(dto.getFechaTarea());
        entity.setDescripcionTarea(dto.getDescripcionTarea());
        return entity;
    }
    public void updateEntityFromDto(TareaDTO dto, TareaEntity entity) {
        entity.setIdDesarrollador(dto.getIdDesarrollador());
        entity.setIdProyecto(dto.getIdProyecto());
        entity.setFechaTarea(dto.getFechaTarea());
        entity.setDescripcionTarea(dto.getDescripcionTarea());

    }
}
