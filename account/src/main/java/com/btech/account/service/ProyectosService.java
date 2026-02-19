package com.btech.account.service;


import com.btech.account.entities.ProyectosDTO;
import com.btech.account.mapper.ProyectoMapper;
import com.btech.account.mapper.ProyectoMapperImp;
import com.btech.account.repository.ProyectoRepository;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProyectosService {

    private final ProyectoRepository proyectosRepository;
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;
    private static final String CRM_MICRO_SERVICE = "crm-micro";
    private final ProyectoMapperImp proyectoMapperImp;

    public ProyectosService(ProyectoRepository proyectosRepository, RestTemplate restTemplate, DiscoveryClient discoveryClient, ProyectoMapperImp proyectoMapperImp) {
        this.proyectosRepository = proyectosRepository;
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
        this.proyectoMapperImp = proyectoMapperImp;
    }


    private String getCrmMicroServiceUrl() {
        List<ServiceInstance> instances = discoveryClient.getInstances(CRM_MICRO_SERVICE);
        if (instances.isEmpty()) {
            throw new RuntimeException("Servicio " + CRM_MICRO_SERVICE + " no disponible");
        }
        return instances.get(0).getUri().toString();
    }

    private boolean existCliente(Long idCliente) {
        return Boolean.TRUE.equals(
                restTemplate.getForObject(getCrmMicroServiceUrl() + "/api/clientes/exists/" + idCliente, Boolean.class)
        );
    }

    public boolean existProyecto(Long id) {
        return proyectosRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public ProyectosDTO get(Long id) {
        ProyectosDTO dto = proyectoMapperImp.proyectoToDTO(proyectosRepository.getReferenceById(id));
        resolveClienteNombre(dto);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ProyectosDTO> getAll() {
        List<ProyectosDTO> list = proyectoMapperImp.proyectoToListDTO(proyectosRepository.findAll());
        list.forEach(this::resolveClienteNombre);
        return list;
    }

    @Transactional
    public ProyectosDTO save(ProyectosDTO proyectosDTO) {
        resolveClienteId(proyectosDTO);
        if (proyectosDTO.getIdCliente() == null) return new ProyectosDTO();
        ProyectosDTO dto = proyectoMapperImp.proyectoToDTO(proyectosRepository.save(proyectoMapperImp.proyectosDTOToEntity(proyectosDTO)));
        resolveClienteNombre(dto);
        return dto;
    }

    @Transactional
    public void Delete(Long id) {
        proyectosRepository.deleteById(id);

    }

    @Transactional(readOnly = true)
    public Optional<ProyectosDTO> getByNombreProyecto(String nombreProyecto) {
        return proyectosRepository.findByNombreProyecto(nombreProyecto).map(e -> {
            ProyectosDTO dto = proyectoMapperImp.proyectoToDTO(e);
            resolveClienteNombre(dto);
            return dto;
        });
    }

    @Transactional
    public Optional<ProyectosDTO> updateByNombreProyecto(String nombreProyecto, ProyectosDTO proyectosDTO) {
        resolveClienteId(proyectosDTO);
        return proyectosRepository.findByNombreProyecto(nombreProyecto).map(entity -> {
            entity.setNombreProyecto(proyectosDTO.getNombreProyecto());
            entity.setIdCliente(proyectosDTO.getIdCliente());
            ProyectosDTO dto = proyectoMapperImp.proyectoToDTO(proyectosRepository.save(entity));
            resolveClienteNombre(dto);
            return dto;
        });
    }

    @Transactional
    public boolean deleteByNombreProyecto(String nombreProyecto) {
        if (proyectosRepository.findByNombreProyecto(nombreProyecto).isEmpty()) return false;
        proyectosRepository.deleteByNombreProyecto(nombreProyecto);
        return true;
    }

    private void resolveClienteNombre(ProyectosDTO dto) {
        if (dto == null || dto.getIdCliente() == null) return;
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> cliente = restTemplate.getForObject(
                    getCrmMicroServiceUrl() + "/api/clientes/" + dto.getIdCliente(), Map.class);
            if (cliente != null && cliente.get("nombre") != null) {
                dto.setClienteNombre((String) cliente.get("nombre"));
            }
        } catch (Exception e) {
            dto.setClienteNombre("(no disponible)");
        }
    }

    private void resolveClienteId(ProyectosDTO dto) {
        if (dto == null || dto.getClienteNombre() == null || dto.getClienteNombre().isBlank()) return;
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> cliente = restTemplate.getForObject(
                    getCrmMicroServiceUrl() + "/api/clientes/buscar?nombre=" + dto.getClienteNombre(), Map.class);
            if (cliente != null && cliente.get("id") != null) {
                dto.setIdCliente(((Number) cliente.get("id")).longValue());
            }
        } catch (Exception e) {
            dto.setIdCliente(null);
        }
    }
}
