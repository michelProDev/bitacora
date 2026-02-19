package com.btech.hr.mapper;

import com.btech.hr.dto.RegistroDTO;
import com.btech.hr.entity.RegistroEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RegistroMapperImp implements RegistroMapper {

    @Override
    public RegistroDTO registroToDTO(RegistroEntity registroEntity) {
        return registroEntity == null
                ? new RegistroDTO()
                : new RegistroDTO(  
                registroEntity.getId(),
                registroEntity.getFecha(),
                registroEntity.getRecurso(),
                registroEntity.getProyecto(),
                registroEntity.getHoras(),
                registroEntity.getDescripcion(),
                registroEntity.getTipoRegistroId(),
                null,
                registroEntity.getEstadoTareaId(),
                null,
                null,
                null
        );
    }

    @Override
    public RegistroEntity registroDTOToEntity(RegistroDTO registroDTO) {
        return registroDTO == null
                ? new RegistroEntity()
                : new RegistroEntity(
                registroDTO.getId(),
                registroDTO.getFecha(),
                registroDTO.getRecurso(),
                registroDTO.getProyecto(),
                registroDTO.getHoras(),
                registroDTO.getDescripcion(),
                registroDTO.getTipoRegistroId(),
                registroDTO.getEstadoTareaId()
        );
    }

    @Override
    public List<RegistroDTO> registroToListDTO(List<RegistroEntity> registroEntities) {
        return registroEntities.stream()
                .map(this::registroToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RegistroEntity> registroDTOToListEntity(List<RegistroDTO> registroDTOs) {
        return registroDTOs.stream()
                .map(this::registroDTOToEntity)
                .collect(Collectors.toList());
    }
}
