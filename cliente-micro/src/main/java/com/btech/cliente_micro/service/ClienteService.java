package com.btech.cliente_micro.service;

import com.btech.cliente_micro.dto.ClienteDTO;
import com.btech.cliente_micro.repository.ClienteRepository;
import com.btech.cliente_micro.mapper.ClienteMapperImp;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapperImp clienteMapperImp;
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;
    private static final String CATALOGOS_MICRO_SERVICE = "catalogos-micro";

    public ClienteService(ClienteRepository clienteRepository, ClienteMapperImp clienteMapperImp, RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.clienteRepository = clienteRepository;
        this.clienteMapperImp = clienteMapperImp;
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    private String getCatalogosMicroServiceUrl() {
        List<ServiceInstance> instances = discoveryClient.getInstances(CATALOGOS_MICRO_SERVICE);
        if (instances.isEmpty()) {
            throw new RuntimeException("Servicio " + CATALOGOS_MICRO_SERVICE + " no disponible");
        }
        return instances.get(0).getUri().toString();
    }

    @Transactional(readOnly = true)
    public ClienteDTO get(Long id) {
        ClienteDTO dto = clienteMapperImp.clienteToDTO(clienteRepository.getReferenceById(id));
        resolvePaisNombre(dto);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> getAll() {
        List<ClienteDTO> list = clienteMapperImp.clienteToListDTO(clienteRepository.findAll());
        list.forEach(this::resolvePaisNombre);
        return list;
    }

    @Transactional
    public ClienteDTO save(ClienteDTO clienteDTO) {
        ClienteDTO dto = clienteMapperImp.clienteToDTO(clienteRepository.save(clienteMapperImp.clienteDToToEntity(clienteDTO)));
        resolvePaisNombre(dto);
        return dto;
    }

    @Transactional
    public void Delete (Long id){
        clienteRepository.deleteById(id);

    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id){
        return clienteRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public Optional<ClienteDTO> getByNombre(String nombre) {
        return clienteRepository.findByNombre(nombre).map(e -> {
            ClienteDTO dto = clienteMapperImp.clienteToDTO(e);
            resolvePaisNombre(dto);
            return dto;
        });
    }

    @Transactional
    public Optional<ClienteDTO> updateByNombre(String nombre, ClienteDTO clienteDTO) {
        return clienteRepository.findByNombre(nombre).map(entity -> {
            entity.setNombre(clienteDTO.getNombre());
            entity.setPaisId(clienteDTO.getPaisId());
            ClienteDTO dto = clienteMapperImp.clienteToDTO(clienteRepository.save(entity));
            resolvePaisNombre(dto);
            return dto;
        });
    }

    @Transactional
    public boolean deleteByNombre(String nombre) {
        if (clienteRepository.findByNombre(nombre).isEmpty()) return false;
        clienteRepository.deleteByNombre(nombre);
        return true;
    }

    private void resolvePaisNombre(ClienteDTO dto) {
        if (dto == null || dto.getPaisId() == null) return;
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> pais = restTemplate.getForObject(
                    getCatalogosMicroServiceUrl() + "/api/paises/" + dto.getPaisId(), Map.class);
            if (pais != null && pais.get("nombre") != null) {
                dto.setPaisNombre((String) pais.get("nombre"));
            }
        } catch (Exception e) {
            dto.setPaisNombre("(no disponible)");
        }
    }

}
