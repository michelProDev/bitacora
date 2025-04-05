package com.btech.bitacora.service;

import com.btech.bitacora.model.dto.ProjectDTO;
import com.btech.bitacora.model.entities.Project;
import com.btech.bitacora.model.mapper.ProjectMapper;
import com.btech.bitacora.repository.IProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService {

    private final IProjectRepository ProjectRepository;
    private final ProjectMapper ProjectMapper;


    public ProjectService(IProjectRepository ProjectRepository, ProjectMapper ProjectMapper) {
        this.ProjectRepository = ProjectRepository;
        this.ProjectMapper = ProjectMapper;
    }

    public ProjectDTO createProject(ProjectDTO ProjectDTO) {
        Project Project = ProjectMapper.toEntity(ProjectDTO);
        Project = ProjectRepository.save(Project);
        return ProjectMapper.toDto(Project);
    }

    public List<ProjectDTO> getAllProjects() {
        return ProjectRepository.findAll()
                .stream()
                .map(ProjectMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ProjectDTO> getProjectById(Long id) {
        return ProjectRepository.findById(id)
                .map(ProjectMapper::toDto);
    }

    public Optional<ProjectDTO> updateProject(Long id, ProjectDTO projectDTO) {
        return ProjectRepository.findById(id)
                .map(existingProject -> {
                    existingProject.setNombreProyecto(projectDTO.getNombreProyecto());
                    existingProject.setCliente(projectDTO.getCliente());
                    Project updatedProject = ProjectRepository.save(existingProject);
                    return ProjectMapper.toDto(updatedProject);
                });
    }

    public void deleteProject(Long id) {
        ProjectRepository.deleteById(id);
    }


}