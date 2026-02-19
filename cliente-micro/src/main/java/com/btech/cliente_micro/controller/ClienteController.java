package com.btech.cliente_micro.controller;


import com.btech.cliente_micro.dto.ClienteDTO;
import com.btech.cliente_micro.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")

public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAll() {
        return ResponseEntity.ok(clienteService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> get(@PathVariable Long id) {
        if (!clienteService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteService.get(id));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.status(201).body(clienteService.save(clienteDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        if (!clienteService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteService.save(clienteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!clienteService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteService.Delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.existsById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ClienteDTO> getByNombre(@RequestParam String nombre) {
        return clienteService.getByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/nombre/{nombre}")
    public ResponseEntity<ClienteDTO> updateByNombre(@PathVariable String nombre, @RequestBody ClienteDTO clienteDTO) {
        return clienteService.updateByNombre(nombre, clienteDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/nombre/{nombre}")
    public ResponseEntity<Void> deleteByNombre(@PathVariable String nombre) {
        return clienteService.deleteByNombre(nombre)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
