package com.btech.bitacora.service;

import com.btech.bitacora.model.dto.ProyectoDTO;
import com.btech.bitacora.model.entity.ProyectoEntity;
import com.btech.bitacora.model.mapper.ProyectoMapper;
import com.btech.bitacora.repository.IProyectoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProyectoService  {

    private final IProyectoRepository proyectoRepository;
    private final ProyectoMapper proyectoMapper;


    public ProyectoService(IProyectoRepository proyectoRepository,
                               ProyectoMapper proyectoMapper) {
        this.proyectoRepository = proyectoRepository;
        this.proyectoMapper = proyectoMapper;
    }

    public ProyectoDTO crearProyecto(ProyectoDTO proyectoDTO) {
        if (proyectoDTO.getNombre() == null || proyectoDTO.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proyecto no puede estar vacío");
        }

        ProyectoEntity proyectoEntity = proyectoMapper.toEntity(proyectoDTO);
        proyectoEntity = proyectoRepository.save(proyectoEntity);
        return proyectoMapper.toDto(proyectoEntity);
    }

    @Transactional(readOnly = true)
    public ProyectoDTO obtenerProyectoPorId(Long id) {
        ProyectoEntity proyectoEntity = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + id));
        return proyectoMapper.toDto(proyectoEntity);
    }

     @Transactional(readOnly = true)
    public List<ProyectoDTO> listarTodosLosProyectos() {
        return proyectoRepository.findAll()
                .stream()
                .map(proyectoMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProyectoDTO actualizarProyecto(Long id, ProyectoDTO proyectoDTO) {
        ProyectoEntity proyectoExistente = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + id));
    proyectoExistente.setNombre(proyectoDTO.getNombre());
        proyectoExistente.setCliente(proyectoDTO.getCliente());

        ProyectoEntity proyectoActualizado = proyectoRepository.save(proyectoExistente);
        return proyectoMapper.toDto(proyectoActualizado);
    }

     public void eliminarProyecto(Long id) {
        if (!proyectoRepository.existsById(id)) {
            throw new RuntimeException("Proyecto no encontrado con id: " + id);
        }
        proyectoRepository.deleteById(id);
    }
}
