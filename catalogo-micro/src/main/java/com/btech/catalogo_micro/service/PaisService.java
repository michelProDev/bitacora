package com.btech.catalogo_micro.service;

import com.btech.catalogo_micro.dto.PaisDTO;
import com.btech.catalogo_micro.repository.PaisRepository;
import com.btech.catalogo_micro.mapper.PaisMapperImp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PaisService {

    private final PaisRepository paisRepository;
    private final PaisMapperImp paisMapperImp;

    public PaisService(PaisRepository paisRepository, PaisMapperImp paisMapperImp) {
        this.paisRepository = paisRepository;
        this.paisMapperImp = paisMapperImp;
    }

    @Transactional(readOnly = true)
    public PaisDTO get(Long id) {
        return paisMapperImp.toDTO(paisRepository.getReferenceById(id));
    }

    @Transactional(readOnly = true)
    public List<PaisDTO> getAll() {
        return paisMapperImp.toListDTO(paisRepository.findAll());
    }

    @Transactional
    public PaisDTO save(PaisDTO paisDTO) {
        return paisMapperImp.toDTO(paisRepository.save(paisMapperImp.toEntity(paisDTO)));
    }

    @Transactional
    public void Delete(Long id) {
        paisRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return paisRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public Optional<PaisDTO> getByNombre(String nombre) {
        return paisRepository.findByNombre(nombre).map(paisMapperImp::toDTO);
    }

    @Transactional
    public Optional<PaisDTO> updateByNombre(String nombre, PaisDTO paisDTO) {
        return paisRepository.findByNombre(nombre).map(entity -> {
            entity.setNombre(paisDTO.getNombre());
            return paisMapperImp.toDTO(paisRepository.save(entity));
        });
    }

    @Transactional
    public boolean deleteByNombre(String nombre) {
        if (paisRepository.findByNombre(nombre).isEmpty()) return false;
        paisRepository.deleteByNombre(nombre);
        return true;
    }
}
