package com.btech.bitacora.service;

import com.btech.bitacora.model.dto.DesarrolladorDTO;
import com.btech.bitacora.model.dto.DesarrolladorDTO;
import com.btech.bitacora.model.entity.DesarrolladorEntity;
import com.btech.bitacora.model.entity.DesarrolladorEntity;
import com.btech.bitacora.model.mapper.DesarrolladorMapper;
import com.btech.bitacora.repository.IDesarrolladorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DesarrolladorService {

    private final IDesarrolladorRepository desarrolladorRepository;
    private final DesarrolladorMapper desarrolladorMapper;

    public DesarrolladorService(IDesarrolladorRepository desarrolladorRepository,
                                    DesarrolladorMapper desarrolladorMapper) {
        this.desarrolladorRepository = desarrolladorRepository;
        this.desarrolladorMapper = desarrolladorMapper;
    }

    public DesarrolladorDTO crearDesarrollador(DesarrolladorDTO desarrolladorDTO) {
        DesarrolladorEntity desarrolladorEntity = desarrolladorMapper.toEntity(desarrolladorDTO);
        desarrolladorEntity = desarrolladorRepository.save(desarrolladorEntity);
        return desarrolladorMapper.toDto(desarrolladorEntity);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public DesarrolladorDTO obtenerDesarrolladorPorId(Long id) {
        DesarrolladorEntity DesarrolladorEntity = desarrolladorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Desarrollador no encontrado con id: " + id));
        return desarrolladorMapper.toDto(DesarrolladorEntity);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<DesarrolladorDTO> listarTodosLosDesarrolladors() {
        return desarrolladorRepository.findAll()
                .stream()
                .map(desarrolladorMapper::toDto)
                .collect(Collectors.toList());
    }

    public DesarrolladorDTO actualizarDesarrollador(Long id, DesarrolladorDTO DesarrolladorDTO) {
        DesarrolladorEntity DesarrolladorExistente = desarrolladorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Desarrollador no encontrado con id: " + id));
        DesarrolladorExistente.setNombre(DesarrolladorDTO.getNombre());
        DesarrolladorExistente.setCategoria(DesarrolladorDTO.getCategoria());
        DesarrolladorExistente.setPais(DesarrolladorDTO.getPais());
        DesarrolladorEntity DesarrolladorActualizado = desarrolladorRepository.save(DesarrolladorExistente);
        return desarrolladorMapper.toDto(DesarrolladorActualizado);
    }

    public void eliminarDesarrollador(Long id) {
        if (!desarrolladorRepository.existsById(id)) {
            throw new RuntimeException("Desarrollador no encontrado con id: " + id);
        }
        desarrolladorRepository.deleteById(id);
    }
}