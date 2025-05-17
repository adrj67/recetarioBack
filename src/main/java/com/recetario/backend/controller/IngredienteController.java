package com.recetario.backend.controller;

import com.recetario.backend.dtos.IngredienteRequestDTO;
import com.recetario.backend.dtos.IngredienteResponseDTO;
import com.recetario.backend.entities.Ingrediente;
import com.recetario.backend.exception.ResourceNotFoundException;
import com.recetario.backend.repositories.IngredienteRepository;
import com.recetario.backend.services.IngredienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ingredientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Ingredientes", description = "Operaciones sobre los ingredientes")
public class IngredienteController {

    private final IngredienteService ingredienteService;
    private final IngredienteRepository ingredienteRepository;

    // Listar Ingredientes
    @Operation(summary = "Obtener todos los ingredientes", description = "Devuelve una lista de todos los ingredientes")
    @GetMapping
    public ResponseEntity<List<Ingrediente>> listarIngredientes() {
        return ResponseEntity.ok(ingredienteService.listarIngredientes());
    }

    // Crear ingredientes
    @Operation(summary = "Cargar un nuevo ingrediente", description = "Agrega un nuevo ingrediente a la base de datos")
    @PostMapping
    public ResponseEntity<IngredienteResponseDTO> crearIngrediente(@RequestBody IngredienteRequestDTO request) {
        IngredienteResponseDTO creado = ingredienteService.crearIngrediente(request);
        return ResponseEntity.ok(creado);
    }

    // Modificar ingredientes
    @Operation(summary = "Modificar ingrediente", description = "Modifica el ingrediente seleccionado")
    @PutMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> actualizarIngrediente(
            @PathVariable Long id,
            @RequestBody IngredienteRequestDTO request) {
        return ResponseEntity.ok(ingredienteService.actualizarIngrediente(id, request));
    }

    // Borrar Ingrediente
    @Operation(summary = "Elimina un ingrdiente", description = "Elimina un ingrediente salvo que este siendo utilizado en una receta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "No encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIngrediente(@PathVariable Long id) {
        ingredienteService.eliminarIngrediente(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar ingredienete por ID
    @GetMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> obtenerPorId(@PathVariable Long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingrediente no encontrado con id: " + id));

        IngredienteResponseDTO dto = IngredienteResponseDTO.builder()
                .id(ingrediente.getId())
                .nombre(ingrediente.getNombre())
                .unidad(ingrediente.getUnidad())
                .costoUnitario(ingrediente.getCostoUnitario())
                .build();

        return ResponseEntity.ok(dto);
    }

    // Buacar ingredientes por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<IngredienteResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        List<Ingrediente> resultados = ingredienteRepository.findByNombreContainingIgnoreCase(nombre);
        List<IngredienteResponseDTO> dtoList = resultados.stream().map(i -> IngredienteResponseDTO.builder()
                .id(i.getId())
                .nombre(i.getNombre())
                .unidad(i.getUnidad())
                .costoUnitario(i.getCostoUnitario())
                .build()).collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

}
