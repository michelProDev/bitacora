package com.btech.catalogo_micro.controller;

import com.btech.catalogo_micro.dto.EstadoTareaDTO;
import com.btech.catalogo_micro.service.EstadoTareaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados-tarea")
public class EstadoTareaController {
    private final EstadoTareaService estadoTareaService;

    public EstadoTareaController(EstadoTareaService estadoTareaService) {
        this.estadoTareaService = estadoTareaService;
    }

    @GetMapping
    public ResponseEntity<List<EstadoTareaDTO>> getAll() {
        return ResponseEntity.ok(estadoTareaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoTareaDTO> get(@PathVariable Long id) {
        if (!estadoTareaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estadoTareaService.get(id));
    }

    @PostMapping
    public ResponseEntity<EstadoTareaDTO> create(@RequestBody EstadoTareaDTO estadoTareaDTO) {
        return ResponseEntity.status(201).body(estadoTareaService.save(estadoTareaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoTareaDTO> update(@PathVariable Long id, @RequestBody EstadoTareaDTO estadoTareaDTO) {
        if (!estadoTareaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estadoTareaService.save(estadoTareaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!estadoTareaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        estadoTareaService.Delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(estadoTareaService.existsById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<EstadoTareaDTO> getByNombre(@RequestParam String nombre) {
        return estadoTareaService.getByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/nombre/{nombre}")
    public ResponseEntity<EstadoTareaDTO> updateByNombre(@PathVariable String nombre, @RequestBody EstadoTareaDTO estadoTareaDTO) {
        return estadoTareaService.updateByNombre(nombre, estadoTareaDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/nombre/{nombre}")
    public ResponseEntity<Void> deleteByNombre(@PathVariable String nombre) {
        return estadoTareaService.deleteByNombre(nombre)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
