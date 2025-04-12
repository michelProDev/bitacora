package com.btech.bitacora.service;

import com.btech.bitacora.model.dto.TareaDTO;
import com.btech.bitacora.model.entity.TareaEntity;
import com.btech.bitacora.model.mapper.TareaMapper;
import com.btech.bitacora.repository.ITareaRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaService {

    private final ITareaRepository tareaRepository;
    private final TareaMapper tareaMapper;

    @Autowired
    public TareaService(ITareaRepository tareaRepository, TareaMapper tareaMapper) {
        this.tareaRepository = tareaRepository;
        this.tareaMapper = tareaMapper;
    }

    @Transactional
    public TareaDTO crearTarea(TareaDTO tareaDTO) {
        TareaEntity tareaEntity = tareaMapper.toEntity(tareaDTO);
        TareaEntity tareaGuardada = tareaRepository.save(tareaEntity);
        return tareaMapper.toDto(tareaGuardada);
    }

    @Transactional(readOnly = true)
    public TareaDTO obtenerTareaPorId(Long id) {
        TareaEntity tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));
        return tareaMapper.toDto(tarea);
    }
    @Transactional(readOnly = true)
    public List<TareaDTO> listarTodasLasTareas() {
        return tareaRepository.findAll()
                .stream()
                .map(tareaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TareaDTO actualizarTarea(Long id, TareaDTO tareaDTO) {
        TareaEntity tareaExistente = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));

        tareaMapper.updateEntityFromDto(tareaDTO, tareaExistente);
        TareaEntity tareaActualizada = tareaRepository.save(tareaExistente);
        return tareaMapper.toDto(tareaActualizada);
    }

    @Transactional
    public void eliminarTarea(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new RuntimeException("Tarea no encontrada con ID: " + id);
        }
        tareaRepository.deleteById(id);
    }



}