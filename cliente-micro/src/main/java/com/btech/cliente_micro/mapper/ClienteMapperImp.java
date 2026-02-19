package com.btech.cliente_micro.mapper;

import com.btech.cliente_micro.dto.ClienteDTO;
import com.btech.cliente_micro.entity.ClienteEntity;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteMapperImp implements ClienteMapper {

    @Override
    public ClienteDTO clienteToDTO(ClienteEntity clienteEntity) {
        return clienteEntity == null ? new ClienteDTO() : new ClienteDTO(clienteEntity.getId(), clienteEntity.getNombre(), clienteEntity.getPaisId(), null);
    }

    @Override
    public ClienteEntity clienteDToToEntity(ClienteDTO clienteDTO) {
        return clienteDTO == null ? new ClienteEntity() : new ClienteEntity(null, clienteDTO.getNombre(), clienteDTO.getPaisId());
    }

    @Override
    public List<ClienteDTO> clienteToListDTO(List<ClienteEntity> clienteEntities) {
        return clienteEntities.stream()
                .map(this::clienteToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClienteEntity> clienteDTOToListEntity(List<ClienteDTO> clienteDTOs) {
        return clienteDTOs.stream()
                .map(this::clienteDToToEntity)
                .collect(Collectors.toList());
    }
}
