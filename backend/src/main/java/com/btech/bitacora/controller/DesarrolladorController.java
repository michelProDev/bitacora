package com.btech.bitacora.controller;

import com.btech.bitacora.model.dto.DesarrolladorDTO;
import com.btech.bitacora.service.DesarrolladorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/desarrolladores")
@Tag(name = "Desarrolladores API", description = "API para gestión de desarrolladores")
public class DesarrolladorController {

    private final DesarrolladorService desarrolladorService;

    public DesarrolladorController(DesarrolladorService desarrolladorService) {
        this.desarrolladorService = desarrolladorService;
    }

    @Operation(summary = "Crear un nuevo desarrollador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Desarrollador creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<DesarrolladorDTO> crearDesarrollador(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del desarrollador a crear", required = true)
            @Valid @RequestBody DesarrolladorDTO desarrolladorDTO) {
        DesarrolladorDTO nuevoDesarrollador = desarrolladorService.crearDesarrollador(desarrolladorDTO);
        return new ResponseEntity<>(nuevoDesarrollador, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener un desarrollador por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Desarrollador encontrado"),
            @ApiResponse(responseCode = "404", description = "Desarrollador no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DesarrolladorDTO> obtenerDesarrolladorPorId(
            @Parameter(description = "ID del desarrollador", example = "1")
            @PathVariable Long id) {
        DesarrolladorDTO desarrollador = desarrolladorService.obtenerDesarrolladorPorId(id);
        return ResponseEntity.ok(desarrollador);
    }

    @Operation(summary = "Listar todos los desarrolladores")
    @ApiResponse(responseCode = "200", description = "Lista de desarrolladores obtenida")
    @GetMapping
    public ResponseEntity<List<DesarrolladorDTO>> listarTodosLosDesarrolladores() {
        List<DesarrolladorDTO> desarrolladores = desarrolladorService.listarTodosLosDesarrolladors();
        return ResponseEntity.ok(desarrolladores);
    }

    @Operation(summary = "Actualizar un desarrollador existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Desarrollador actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Desarrollador no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DesarrolladorDTO> actualizarDesarrollador(
            @Parameter(description = "ID del desarrollador a actualizar", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos actualizados del desarrollador", required = true)
            @Valid @RequestBody DesarrolladorDTO desarrolladorDTO) {
        DesarrolladorDTO desarrolladorActualizado = desarrolladorService.actualizarDesarrollador(id, desarrolladorDTO);
        return ResponseEntity.ok(desarrolladorActualizado);
    }

    @Operation(summary = "Eliminar un desarrollador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Desarrollador eliminado"),
            @ApiResponse(responseCode = "404", description = "Desarrollador no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDesarrollador(
            @Parameter(description = "ID del desarrollador a eliminar", example = "1")
            @PathVariable Long id) {
        desarrolladorService.eliminarDesarrollador(id);
        return ResponseEntity.noContent().build();
    }




}