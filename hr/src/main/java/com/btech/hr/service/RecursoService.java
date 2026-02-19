package com.btech.hr.service;

import com.btech.hr.dto.RecursoDTO;
import com.btech.hr.entity.RecursoEntity;
import com.btech.hr.mapper.RecursoMapperImp;
import com.btech.hr.repository.RecursoRepository;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecursoService {

    private final RecursoRepository recursoRepository;
    private final RecursoMapperImp recursoMapper;
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;
    private final PasswordEncoder passwordEncoder;
    private static final String CATALOGOS_MICRO_SERVICE = "catalogos-micro";

    public RecursoService(RecursoRepository recursoRepository, RecursoMapperImp recursoMapper, RestTemplate restTemplate,
                          DiscoveryClient discoveryClient, PasswordEncoder passwordEncoder) {
        this.recursoRepository = recursoRepository;
        this.recursoMapper = recursoMapper;
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
        this.passwordEncoder = passwordEncoder;
    }

    private String getCatalogosMicroServiceUrl() {
        List<ServiceInstance> instances = discoveryClient.getInstances(CATALOGOS_MICRO_SERVICE);
        if (instances.isEmpty()) {
            throw new RuntimeException("Servicio " + CATALOGOS_MICRO_SERVICE + " no disponible");
        }
        return instances.get(0).getUri().toString();
    }

    @Transactional(readOnly = true)
    public Optional<RecursoDTO> get(Long id) {
        return recursoRepository.findById(id).map(e -> {
            RecursoDTO dto = recursoMapper.recursoToDTO(e);
            resolveCategoriaNombre(dto);
            return dto;
        });
    }

    @Transactional(readOnly = true)
    public List<RecursoDTO> getAll() {
        List<RecursoDTO> list = recursoMapper.recursoToListDTO(recursoRepository.findAll());
        list.forEach(this::resolveCategoriaNombre);
        return list;
    }

    @Transactional
    public RecursoDTO create(RecursoDTO recursoDTO) {
        RecursoEntity entity = recursoMapper.recursoDTOToEntity(recursoDTO);
        entity.setId(null);
        if (entity.getPassword() != null && !entity.getPassword().isBlank()) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        if (entity.getRol() == null || entity.getRol().isBlank()) {
            entity.setRol("USUARIO");
        }
        RecursoDTO dto = recursoMapper.recursoToDTO(recursoRepository.save(entity));
        resolveCategoriaNombre(dto);
        return dto;
    }

    @Transactional
    public Optional<RecursoDTO> update(Long id, RecursoDTO recursoDTO) {
        if (!recursoRepository.existsById(id)) {
            return Optional.empty();
        }

        RecursoEntity entity = recursoMapper.recursoDTOToEntity(recursoDTO);
        entity.setId(id);
        if (entity.getPassword() != null && !entity.getPassword().isBlank()) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        } else {
            recursoRepository.findById(id).ifPresent(existing -> entity.setPassword(existing.getPassword()));
        }
        RecursoDTO dto = recursoMapper.recursoToDTO(recursoRepository.save(entity));
        resolveCategoriaNombre(dto);
        return Optional.of(dto);
    }

    @Transactional
    public boolean delete(Long id) {
        if (!recursoRepository.existsById(id)) {
            return false;
        }
        recursoRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return recursoRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public Optional<RecursoDTO> getByNombre(String nombre) {
        return recursoRepository.findByNombre(nombre).map(e -> {
            RecursoDTO dto = recursoMapper.recursoToDTO(e);
            resolveCategoriaNombre(dto);
            return dto;
        });
    }

    @Transactional
    public Optional<RecursoDTO> updateByNombre(String nombre, RecursoDTO recursoDTO) {
        return recursoRepository.findByNombre(nombre).map(entity -> {
            entity.setNombre(recursoDTO.getNombre());
            entity.setApellidos(recursoDTO.getApellidos());
            entity.setCategoriaId(recursoDTO.getCategoriaId());
            entity.setTecnologia(recursoDTO.getTecnologia());
            if (recursoDTO.getCorreo() != null) entity.setCorreo(recursoDTO.getCorreo());
            if (recursoDTO.getRol() != null) entity.setRol(recursoDTO.getRol());
            if (recursoDTO.getPassword() != null && !recursoDTO.getPassword().isBlank()) {
                entity.setPassword(passwordEncoder.encode(recursoDTO.getPassword()));
            }
            RecursoDTO dto = recursoMapper.recursoToDTO(recursoRepository.save(entity));
            resolveCategoriaNombre(dto);
            return dto;
        });
    }

    @Transactional
    public boolean deleteByNombre(String nombre) {
        if (recursoRepository.findByNombre(nombre).isEmpty()) return false;
        recursoRepository.deleteByNombre(nombre);
        return true;
    }

    private void resolveCategoriaNombre(RecursoDTO dto) {
        if (dto == null || dto.getCategoriaId() == null) return;
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> categoria = restTemplate.getForObject(
                    getCatalogosMicroServiceUrl() + "/api/categorias/" + dto.getCategoriaId(), Map.class);
            if (categoria != null && categoria.get("nombre") != null) {
                dto.setCategoriaNombre((String) categoria.get("nombre"));
            }
        } catch (Exception e) {
            dto.setCategoriaNombre("(no disponible)");
        }
    }
}
