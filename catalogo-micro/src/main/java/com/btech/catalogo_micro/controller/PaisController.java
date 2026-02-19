package com.btech.catalogo_micro.controller;

import com.btech.catalogo_micro.dto.PaisDTO;
import com.btech.catalogo_micro.service.PaisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paises")
public class PaisController {
    private final PaisService paisService;

    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    @GetMapping
    public ResponseEntity<List<PaisDTO>> getAll() {
        return ResponseEntity.ok(paisService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaisDTO> get(@PathVariable Long id) {
        if (!paisService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paisService.get(id));
    }

    @PostMapping
    public ResponseEntity<PaisDTO> create(@RequestBody PaisDTO paisDTO) {
        return ResponseEntity.status(201).body(paisService.save(paisDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaisDTO> update(@PathVariable Long id, @RequestBody PaisDTO paisDTO) {
        if (!paisService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paisService.save(paisDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!paisService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        paisService.Delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(paisService.existsById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<PaisDTO> getByNombre(@RequestParam String nombre) {
        return paisService.getByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/nombre/{nombre}")
    public ResponseEntity<PaisDTO> updateByNombre(@PathVariable String nombre, @RequestBody PaisDTO paisDTO) {
        return paisService.updateByNombre(nombre, paisDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/nombre/{nombre}")
    public ResponseEntity<Void> deleteByNombre(@PathVariable String nombre) {
        return paisService.deleteByNombre(nombre)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
