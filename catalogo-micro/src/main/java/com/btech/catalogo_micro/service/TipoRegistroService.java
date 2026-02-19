package com.btech.catalogo_micro.service;

import com.btech.catalogo_micro.dto.TipoRegistroDTO;
import com.btech.catalogo_micro.repository.TipoRegistroRepository;
import com.btech.catalogo_micro.mapper.TipoRegistroMapperImp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TipoRegistroService {

    private final TipoRegistroRepository tipoRegistroRepository;
    private final TipoRegistroMapperImp tipoRegistroMapperImp;

    public TipoRegistroService(TipoRegistroRepository tipoRegistroRepository, TipoRegistroMapperImp tipoRegistroMapperImp) {
        this.tipoRegistroRepository = tipoRegistroRepository;
        this.tipoRegistroMapperImp = tipoRegistroMapperImp;
    }

    @Transactional(readOnly = true)
    public TipoRegistroDTO get(Long id) {
        return tipoRegistroMapperImp.toDTO(tipoRegistroRepository.getReferenceById(id));
    }

    @Transactional(readOnly = true)
    public List<TipoRegistroDTO> getAll() {
        return tipoRegistroMapperImp.toListDTO(tipoRegistroRepository.findAll());
    }

    @Transactional
    public TipoRegistroDTO save(TipoRegistroDTO tipoRegistroDTO) {
        return tipoRegistroMapperImp.toDTO(tipoRegistroRepository.save(tipoRegistroMapperImp.toEntity(tipoRegistroDTO)));
    }

    @Transactional
    public void Delete(Long id) {
        tipoRegistroRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return tipoRegistroRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public Optional<TipoRegistroDTO> getByNombre(String nombre) {
        return tipoRegistroRepository.findByNombre(nombre).map(tipoRegistroMapperImp::toDTO);
    }

    @Transactional
    public Optional<TipoRegistroDTO> updateByNombre(String nombre, TipoRegistroDTO tipoRegistroDTO) {
        return tipoRegistroRepository.findByNombre(nombre).map(entity -> {
            entity.setNombre(tipoRegistroDTO.getNombre());
            return tipoRegistroMapperImp.toDTO(tipoRegistroRepository.save(entity));
        });
    }

    @Transactional
    public boolean deleteByNombre(String nombre) {
        if (tipoRegistroRepository.findByNombre(nombre).isEmpty()) return false;
        tipoRegistroRepository.deleteByNombre(nombre);
        return true;
    }
}
