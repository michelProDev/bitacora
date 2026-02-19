package com.btech.account.controller;


import com.btech.account.entities.ProyectosDTO;
import com.btech.account.service.ProyectosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    private final ProyectosService proyectosService;

    public ProyectoController(ProyectosService proyectosService) {
        this.proyectosService = proyectosService;
    }

    @GetMapping
    public ResponseEntity<List<ProyectosDTO>> getAll() {
        return ResponseEntity.ok(proyectosService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectosDTO> get(@PathVariable Long id) {
        if (!proyectosService.existProyecto(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proyectosService.get(id));
    }

    @PostMapping
    public ResponseEntity<ProyectosDTO> create(@RequestBody ProyectosDTO proyectosDTO) {
        return ResponseEntity.status(201).body(proyectosService.save(proyectosDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectosDTO> update(@PathVariable Long id, @RequestBody ProyectosDTO proyectosDTO) {
        if (!proyectosService.existProyecto(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proyectosService.save(proyectosDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!proyectosService.existProyecto(id)) {
            return ResponseEntity.notFound().build();
        }
        proyectosService.Delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(proyectosService.existProyecto(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ProyectosDTO> getByNombre(@RequestParam String nombre) {
        return proyectosService.getByNombreProyecto(nombre)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/nombre/{nombre}")
    public ResponseEntity<ProyectosDTO> updateByNombre(@PathVariable String nombre, @RequestBody ProyectosDTO proyectosDTO) {
        return proyectosService.updateByNombreProyecto(nombre, proyectosDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/nombre/{nombre}")
    public ResponseEntity<Void> deleteByNombre(@PathVariable String nombre) {
        return proyectosService.deleteByNombreProyecto(nombre)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
