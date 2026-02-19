package com.btech.hr.service;

import com.btech.hr.dto.RegistroDTO;
import com.btech.hr.entity.RegistroEntity;
import com.btech.hr.mapper.RegistroMapperImp;
import com.btech.hr.repository.RecursoRepository;
import com.btech.hr.repository.RegistroRepository;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RegistroService {

    private static final String ACCOUNT_MICRO_SERVICE = "account-micro";
    private static final String CATALOGOS_MICRO_SERVICE = "catalogos-micro";

    private final RegistroRepository registroRepository;
    private final RecursoRepository recursoRepository;
    private final RegistroMapperImp registroMapper;
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    public RegistroService(RegistroRepository registroRepository, RecursoRepository recursoRepository, RegistroMapperImp registroMapper, RestTemplate restTemplate,
                           DiscoveryClient discoveryClient) {
        this.registroRepository = registroRepository;
        this.recursoRepository = recursoRepository;
        this.registroMapper = registroMapper;
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    private String getServiceBaseUrl(String serviceId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (instances.isEmpty()) {
            throw new RuntimeException("Servicio " + serviceId + " no disponible");
        }
        return instances.get(0).getUri().toString();
    }

    private boolean existProyecto(Long idProyecto) {
        if (idProyecto == null) {
            return false;
        }
        return Boolean.TRUE.equals(
                restTemplate.getForObject(getServiceBaseUrl(ACCOUNT_MICRO_SERVICE) + "/api/proyectos/exists/" + idProyecto, Boolean.class)
        );
    }

    private boolean validateReferences(RegistroDTO registroDTO) {
        return existProyecto(registroDTO.getProyecto());
    }

    @Transactional(readOnly = true)
    public Optional<RegistroDTO> get(Long id) {
        return registroRepository.findById(id).map(e -> {
            RegistroDTO dto = registroMapper.registroToDTO(e);
            resolveCatalogNames(dto);
            return dto;
        });
    }

    @Transactional(readOnly = true)
    public List<RegistroDTO> getAll() {
        List<RegistroDTO> list = registroMapper.registroToListDTO(registroRepository.findAll());
        list.forEach(this::resolveCatalogNames);
        return list;
    }

    @Transactional
    public Optional<RegistroDTO> create(RegistroDTO registroDTO, String correoAutenticado) {
        var recursoAuth = recursoRepository.findByCorreo(correoAutenticado).orElse(null);
        if (recursoAuth != null && "USUARIO".equalsIgnoreCase(recursoAuth.getRol())) {
            registroDTO.setRecurso(recursoAuth.getId());
            registroDTO.setRecursoNombre(recursoAuth.getNombre());
        } else {
            resolveRecursoId(registroDTO);
        }
        resolveProyectoId(registroDTO);
        if (!validateReferences(registroDTO)) {
            return Optional.empty();
        }
        RegistroEntity entity = registroMapper.registroDTOToEntity(registroDTO);
        entity.setId(null);
        RegistroDTO dto = registroMapper.registroToDTO(registroRepository.save(entity));
        resolveCatalogNames(dto);
        return Optional.of(dto);
    }

    @Transactional
    public Optional<RegistroDTO> update(Long id, RegistroDTO registroDTO) {
        if (!registroRepository.existsById(id)) {
            return Optional.empty();
        }
        resolveIds(registroDTO);
        if (!validateReferences(registroDTO)) {
            return Optional.empty();
        }
        RegistroEntity entity = registroMapper.registroDTOToEntity(registroDTO);
        entity.setId(id);
        RegistroDTO dto = registroMapper.registroToDTO(registroRepository.save(entity));
        resolveCatalogNames(dto);
        return Optional.of(dto);
    }

    @Transactional
    public boolean delete(Long id) {
        if (!registroRepository.existsById(id)) {
            return false;
        }
        registroRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return registroRepository.existsById(id);
    }

    private void resolveCatalogNames(RegistroDTO dto) {
        if (dto == null) return;
        resolveTipoRegistroNombre(dto);
        resolveEstadoTareaNombre(dto);
        resolveRecursoNombre(dto);
        resolveProyectoNombre(dto);
    }

    private void resolveTipoRegistroNombre(RegistroDTO dto) {
        if (dto.getTipoRegistroId() == null) return;
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> tipo = restTemplate.getForObject(
                    getServiceBaseUrl(CATALOGOS_MICRO_SERVICE) + "/api/tipos-registro/" + dto.getTipoRegistroId(), Map.class);
            if (tipo != null && tipo.get("nombre") != null) {
                dto.setTipoRegistroNombre((String) tipo.get("nombre"));
            }
        } catch (Exception e) {
            dto.setTipoRegistroNombre("(no disponible)");
        }
    }

    private void resolveEstadoTareaNombre(RegistroDTO dto) {
        if (dto.getEstadoTareaId() == null) return;
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> estado = restTemplate.getForObject(
                    getServiceBaseUrl(CATALOGOS_MICRO_SERVICE) + "/api/estados-tarea/" + dto.getEstadoTareaId(), Map.class);
            if (estado != null && estado.get("nombre") != null) {
                dto.setEstadoTareaNombre((String) estado.get("nombre"));
            }
        } catch (Exception e) {
            dto.setEstadoTareaNombre("(no disponible)");
        }
    }

    private void resolveRecursoNombre(RegistroDTO dto) {
        if (dto.getRecurso() == null) return;
        recursoRepository.findById(dto.getRecurso()).ifPresentOrElse(
                entity -> dto.setRecursoNombre(entity.getNombre()),
                () -> dto.setRecursoNombre("(no disponible)")
        );
    }

    private void resolveProyectoNombre(RegistroDTO dto) {
        if (dto.getProyecto() == null) return;
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> proyecto = restTemplate.getForObject(
                    getServiceBaseUrl(ACCOUNT_MICRO_SERVICE) + "/api/proyectos/" + dto.getProyecto(), Map.class);
            if (proyecto != null && proyecto.get("nombreProyecto") != null) {
                dto.setProyectoNombre((String) proyecto.get("nombreProyecto"));
            }
        } catch (Exception e) {
            dto.setProyectoNombre("(no disponible)");
        }
    }

    private void resolveIds(RegistroDTO dto) {
        if (dto == null) return;
        resolveRecursoId(dto);
        resolveProyectoId(dto);
    }

    private void resolveRecursoId(RegistroDTO dto) {
        if (dto.getRecursoNombre() == null || dto.getRecursoNombre().isBlank()) return;
        recursoRepository.findByNombre(dto.getRecursoNombre()).ifPresent(
                entity -> dto.setRecurso(entity.getId())
        );
    }

    private void resolveProyectoId(RegistroDTO dto) {
        if (dto.getProyectoNombre() == null || dto.getProyectoNombre().isBlank()) return;
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> proyecto = restTemplate.getForObject(
                    getServiceBaseUrl(ACCOUNT_MICRO_SERVICE) + "/api/proyectos/buscar?nombre=" + dto.getProyectoNombre(), Map.class);
            if (proyecto != null && proyecto.get("id") != null) {
                dto.setProyecto(((Number) proyecto.get("id")).longValue());
            }
        } catch (Exception e) {
            dto.setProyecto(null);
        }
    }

}
