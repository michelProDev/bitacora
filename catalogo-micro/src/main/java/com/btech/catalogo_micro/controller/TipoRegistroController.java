package com.btech.catalogo_micro.controller;

import com.btech.catalogo_micro.dto.TipoRegistroDTO;
import com.btech.catalogo_micro.service.TipoRegistroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-registro")
public class TipoRegistroController {
    private final TipoRegistroService tipoRegistroService;

    public TipoRegistroController(TipoRegistroService tipoRegistroService) {
        this.tipoRegistroService = tipoRegistroService;
    }

    @GetMapping
    public ResponseEntity<List<TipoRegistroDTO>> getAll() {
        return ResponseEntity.ok(tipoRegistroService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoRegistroDTO> get(@PathVariable Long id) {
        if (!tipoRegistroService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipoRegistroService.get(id));
    }

    @PostMapping
    public ResponseEntity<TipoRegistroDTO> create(@RequestBody TipoRegistroDTO tipoRegistroDTO) {
        return ResponseEntity.status(201).body(tipoRegistroService.save(tipoRegistroDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoRegistroDTO> update(@PathVariable Long id, @RequestBody TipoRegistroDTO tipoRegistroDTO) {
        if (!tipoRegistroService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipoRegistroService.save(tipoRegistroDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!tipoRegistroService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tipoRegistroService.Delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(tipoRegistroService.existsById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<TipoRegistroDTO> getByNombre(@RequestParam String nombre) {
        return tipoRegistroService.getByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/nombre/{nombre}")
    public ResponseEntity<TipoRegistroDTO> updateByNombre(@PathVariable String nombre, @RequestBody TipoRegistroDTO tipoRegistroDTO) {
        return tipoRegistroService.updateByNombre(nombre, tipoRegistroDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/nombre/{nombre}")
    public ResponseEntity<Void> deleteByNombre(@PathVariable String nombre) {
        return tipoRegistroService.deleteByNombre(nombre)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
