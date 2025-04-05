package com.btech.bitacora.model.controller;


import com.btech.bitacora.model.dto.ProjectDTO;
import com.btech.bitacora.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Projects")
public class ProjectController {

    private final ProjectService proyectoService;



    public ProjectController(ProjectService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO ProjectDTO) {
        ProjectDTO createdProject = ProjectService.createProject(ProjectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        ResponseEntity<List<ProjectDTO>> ok = ResponseEntity.ok(ProjectService.getAllProjects());
        return ok;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        ResponseEntity<ProjectDTO> projectDTOResponseEntity = ProjectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        return projectDTOResponseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(
            @PathVariable Long id,
             @RequestBody ProjectDTO ProjectDTO) {
        ResponseEntity<ProjectDTO> projectDTOResponseEntity = ProjectService.updateProject(id, ProjectDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        return projectDTOResponseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {

        ProjectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}