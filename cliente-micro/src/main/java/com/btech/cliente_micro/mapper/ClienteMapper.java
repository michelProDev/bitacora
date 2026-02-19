package com.btech.cliente_micro.mapper;

import com.btech.cliente_micro.dto.ClienteDTO;
import com.btech.cliente_micro.entity.ClienteEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClienteMapper {

    ClienteDTO clienteToDTO(ClienteEntity clienteEntity);

    ClienteEntity clienteDToToEntity(ClienteDTO clienteDTO);

    List<ClienteDTO> clienteToListDTO (List<ClienteEntity> clienteEntities);

    List<ClienteEntity> clienteDTOToListEntity (List<ClienteDTO> clienteDTOs);


}
