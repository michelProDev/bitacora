package com.btech.hr.mapper;

import com.btech.hr.dto.RecursoDTO;
import com.btech.hr.entity.RecursoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecursoMapperImp implements RecursoMapper {

    @Override
    public RecursoDTO recursoToDTO(RecursoEntity recursoEntity) {
        return recursoEntity == null
                ? new RecursoDTO()
                : new RecursoDTO(
                recursoEntity.getId(),
                recursoEntity.getNombre(),
                recursoEntity.getApellidos(),
                recursoEntity.getCategoriaId(),
                null,
                recursoEntity.getTecnologia(),
                recursoEntity.getCorreo(),
                null,
                recursoEntity.getRol()
        );
    }

    @Override
    public RecursoEntity recursoDTOToEntity(RecursoDTO recursoDTO) {
        return recursoDTO == null
                ? new RecursoEntity()
                : new RecursoEntity(
                recursoDTO.getId(),
                recursoDTO.getNombre(),
                recursoDTO.getApellidos(),
                recursoDTO.getCategoriaId(),
                recursoDTO.getTecnologia(),
                recursoDTO.getCorreo(),
                recursoDTO.getPassword(),
                recursoDTO.getRol()
        );
    }

    @Override
    public List<RecursoDTO> recursoToListDTO(List<RecursoEntity> recursoEntities) {
        return recursoEntities.stream()
                .map(this::recursoToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecursoEntity> recursoDTOToListEntity(List<RecursoDTO> recursoDTOs) {
        return recursoDTOs.stream()
                .map(this::recursoDTOToEntity)
                .collect(Collectors.toList());
    }
}
