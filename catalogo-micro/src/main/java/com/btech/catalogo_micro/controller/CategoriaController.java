package com.btech.catalogo_micro.controller;

import com.btech.catalogo_micro.dto.CategoriaDTO;
import com.btech.catalogo_micro.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAll() {
        return ResponseEntity.ok(categoriaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> get(@PathVariable Long id) {
        if (!categoriaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoriaService.get(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> create(@RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.status(201).body(categoriaService.save(categoriaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> update(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        if (!categoriaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoriaService.save(categoriaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!categoriaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriaService.Delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.existsById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<CategoriaDTO> getByNombre(@RequestParam String nombre) {
        return categoriaService.getByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/nombre/{nombre}")
    public ResponseEntity<CategoriaDTO> updateByNombre(@PathVariable String nombre, @RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.updateByNombre(nombre, categoriaDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/nombre/{nombre}")
    public ResponseEntity<Void> deleteByNombre(@PathVariable String nombre) {
        return categoriaService.deleteByNombre(nombre)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
