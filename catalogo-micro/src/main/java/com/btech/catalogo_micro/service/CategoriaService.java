package com.btech.catalogo_micro.service;

import com.btech.catalogo_micro.dto.CategoriaDTO;
import com.btech.catalogo_micro.repository.CategoriaRepository;
import com.btech.catalogo_micro.mapper.CategoriaMapperImp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapperImp categoriaMapperImp;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapperImp categoriaMapperImp) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapperImp = categoriaMapperImp;
    }

    @Transactional(readOnly = true)
    public CategoriaDTO get(Long id) {
        return categoriaMapperImp.toDTO(categoriaRepository.getReferenceById(id));
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> getAll() {
        return categoriaMapperImp.toListDTO(categoriaRepository.findAll());
    }

    @Transactional
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        return categoriaMapperImp.toDTO(categoriaRepository.save(categoriaMapperImp.toEntity(categoriaDTO)));
    }

    @Transactional
    public void Delete(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return categoriaRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public Optional<CategoriaDTO> getByNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre).map(categoriaMapperImp::toDTO);
    }

    @Transactional
    public Optional<CategoriaDTO> updateByNombre(String nombre, CategoriaDTO categoriaDTO) {
        return categoriaRepository.findByNombre(nombre).map(entity -> {
            entity.setNombre(categoriaDTO.getNombre());
            return categoriaMapperImp.toDTO(categoriaRepository.save(entity));
        });
    }

    @Transactional
    public boolean deleteByNombre(String nombre) {
        if (categoriaRepository.findByNombre(nombre).isEmpty()) return false;
        categoriaRepository.deleteByNombre(nombre);
        return true;
    }
}
