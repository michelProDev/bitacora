package com.btech.hr.controller;

import com.btech.hr.dto.RecursoDTO;
import com.btech.hr.service.RecursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/recursos")
public class RecursoController {

    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    @GetMapping
    public ResponseEntity<List<RecursoDTO>> getAll() {
        return ResponseEntity.ok(recursoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecursoDTO> getById(@PathVariable Long id) {
        return recursoService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RecursoDTO> create(@RequestBody RecursoDTO recursoDTO) {
        RecursoDTO created = recursoService.create(recursoDTO);
        URI location = URI.create("/api/recursos/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecursoDTO> update(@PathVariable Long id, @RequestBody RecursoDTO recursoDTO) {
        return recursoService.update(id, recursoDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return recursoService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<RecursoDTO> getByNombre(@RequestParam String nombre) {
        return recursoService.getByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/nombre/{nombre}")
    public ResponseEntity<RecursoDTO> updateByNombre(@PathVariable String nombre, @RequestBody RecursoDTO recursoDTO) {
        return recursoService.updateByNombre(nombre, recursoDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/nombre/{nombre}")
    public ResponseEntity<Void> deleteByNombre(@PathVariable String nombre) {
        return recursoService.deleteByNombre(nombre)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
