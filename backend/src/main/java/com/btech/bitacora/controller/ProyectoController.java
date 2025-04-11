package com.btech.bitacora.controller;

import com.btech.bitacora.model.dto.ProyectoDTO;
import com.btech.bitacora.service.ProyectoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @PostMapping
    public ResponseEntity<ProyectoDTO> crearProyecto(@RequestBody ProyectoDTO proyectoDTO) {
        ProyectoDTO nuevoProyecto = proyectoService.crearProyecto(proyectoDTO);
        return new ResponseEntity<>(nuevoProyecto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDTO> obtenerProyectoPorId(@PathVariable Long id) {
        ProyectoDTO proyectoDTO = proyectoService.obtenerProyectoPorId(id);
        return ResponseEntity.ok(proyectoDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProyectoDTO>> listarTodosLosProyectos() {
        List<ProyectoDTO> proyectos = proyectoService.listarTodosLosProyectos();
        return ResponseEntity.ok(proyectos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoDTO> actualizarProyecto(
            @PathVariable Long id,
            @RequestBody ProyectoDTO proyectoDTO) {
        ProyectoDTO proyectoActualizado = proyectoService.actualizarProyecto(id, proyectoDTO);
        return ResponseEntity.ok(proyectoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }

}