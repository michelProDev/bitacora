package com.btech.catalogo_micro.service;

import com.btech.catalogo_micro.dto.EstadoTareaDTO;
import com.btech.catalogo_micro.repository.EstadoTareaRepository;
import com.btech.catalogo_micro.mapper.EstadoTareaMapperImp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoTareaService {

    private final EstadoTareaRepository estadoTareaRepository;
    private final EstadoTareaMapperImp estadoTareaMapperImp;

    public EstadoTareaService(EstadoTareaRepository estadoTareaRepository, EstadoTareaMapperImp estadoTareaMapperImp) {
        this.estadoTareaRepository = estadoTareaRepository;
        this.estadoTareaMapperImp = estadoTareaMapperImp;
    }

    @Transactional(readOnly = true)
    public EstadoTareaDTO get(Long id) {
        return estadoTareaMapperImp.toDTO(estadoTareaRepository.getReferenceById(id));
    }

    @Transactional(readOnly = true)
    public List<EstadoTareaDTO> getAll() {
        return estadoTareaMapperImp.toListDTO(estadoTareaRepository.findAll());
    }

    @Transactional
    public EstadoTareaDTO save(EstadoTareaDTO estadoTareaDTO) {
        return estadoTareaMapperImp.toDTO(estadoTareaRepository.save(estadoTareaMapperImp.toEntity(estadoTareaDTO)));
    }

    @Transactional
    public void Delete(Long id) {
        estadoTareaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return estadoTareaRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public Optional<EstadoTareaDTO> getByNombre(String nombre) {
        return estadoTareaRepository.findByNombre(nombre).map(estadoTareaMapperImp::toDTO);
    }

    @Transactional
    public Optional<EstadoTareaDTO> updateByNombre(String nombre, EstadoTareaDTO estadoTareaDTO) {
        return estadoTareaRepository.findByNombre(nombre).map(entity -> {
            entity.setNombre(estadoTareaDTO.getNombre());
            return estadoTareaMapperImp.toDTO(estadoTareaRepository.save(entity));
        });
    }

    @Transactional
    public boolean deleteByNombre(String nombre) {
        if (estadoTareaRepository.findByNombre(nombre).isEmpty()) return false;
        estadoTareaRepository.deleteByNombre(nombre);
        return true;
    }
}
