package com.btech.hr.controller;

import com.btech.hr.dto.RegistroDTO;
import com.btech.hr.service.RegistroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/registros")
public class RegistroController {

    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @GetMapping
    public ResponseEntity<List<RegistroDTO>> getAll() {
        return ResponseEntity.ok(registroService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroDTO> getById(@PathVariable Long id) {
        return registroService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RegistroDTO> create(@RequestBody RegistroDTO registroDTO, Principal principal) {
        return registroService.create(registroDTO, principal.getName())
                .map(created -> ResponseEntity.created(URI.create("/api/registros/" + created.getId())).body(created))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroDTO> update(@PathVariable Long id, @RequestBody RegistroDTO registroDTO) {
        if (!registroService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        return registroService.update(id, registroDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return registroService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
