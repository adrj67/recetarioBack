/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.recetario.backend.controller;

/**
 *
 * @author adrj
 */

import com.recetario.backend.dtos.RecetaRequestDTO;
import com.recetario.backend.dtos.RecetaResponseDTO;

import com.recetario.backend.services.RecetaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.MediaType;

//import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recetas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Podés ajustar esto según tu frontend
@Tag(name = "Recetas", description = "Operaciones sobre recetas de cocina")
public class RecetaController {

    private final RecetaService recetaService;

    // Listar todas las recetas
    @Operation(summary = "Obtener todas las recetas", description = "Devuelve una lista de todas las recetas guardadas")
    @GetMapping
    public ResponseEntity<List<RecetaResponseDTO>> listarRecetas() {
        return ResponseEntity.ok(recetaService.listarRecetas());
    }

    // Crear Receta
    @Operation(summary = "Crear receta", description = "Agrega una nueva receta a la base de datos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Receta creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error en los datos de entrada")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecetaResponseDTO> crearReceta(@ModelAttribute RecetaRequestDTO request) {
        RecetaResponseDTO respuesta = recetaService.crearReceta(request);
        return ResponseEntity.ok(respuesta);
    }

    // Modificar receta
    @Operation(summary = "Modificar alguna recetas", description = "Modifica la receta seleccionada")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecetaResponseDTO> actualizarReceta(
            @PathVariable Long id,
            @ModelAttribute RecetaRequestDTO request) {
        RecetaResponseDTO actualizada = recetaService.actualizarReceta(id, request);
        return ResponseEntity.ok(actualizada);
    }

    // Borrar receta
    @Operation(summary = "Elimina una receta", description = "Elimina una receta determinada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Long id) {
        recetaService.eliminarReceta(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar receta por ID
    @Operation(summary = "Busca una receta por ID", description = "Devuelve la receta buscada por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Receta encontrada"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RecetaResponseDTO> obtenerRecetaPorId(@PathVariable Long id) {
        RecetaResponseDTO receta = recetaService.buscarPorId(id);
        return ResponseEntity.ok(receta);
    }

    // Buscar receta por nombre
    @Operation(summary = "Busca una receta por nombre", description = "Devuelve una lista de las recetas buscadas por el nombre")
    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<RecetaResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(recetaService.buscarPorNombre(nombre));
    }

    // Buscar receta por autor
    @Operation(summary = "Busca una receta por autor", description = "Devuelve una lista de las recetas buscadas por el autor")
    @GetMapping("/buscar/autor")
    public ResponseEntity<List<RecetaResponseDTO>> buscarPorAutor(@RequestParam String autor) {
        return ResponseEntity.ok(recetaService.buscarPorAutor(autor));
    }

    // Buscar receta por tipo de receta
    @Operation(summary = "Busca una receta por su tipo", description = "Devuelve una lista de las recetas buscadas por el tipo")
    @GetMapping("/buscar/tipo")
    public ResponseEntity<List<RecetaResponseDTO>> buscarPorTipo(@RequestParam String tipo) {
        return ResponseEntity.ok(recetaService.buscarPorTipo(tipo));
    }

    // busca por nombre+autor+tipo
    @Operation(summary = "Busca una receta por nombre, autor o tipo", description = "Devuelve una lista de las recetas buscadas por el nombre, autor o tipo")
    @GetMapping("/buscar")
    public ResponseEntity<List<RecetaResponseDTO>> buscarPorTexto(@RequestParam String texto) {
        return ResponseEntity.ok(recetaService.buscarPorTexto(texto));
    }

    // Mostrar por paginacion
    @Operation(summary = "Paginacion de las busquedas", description = "configuracion de la paginacion para que las busquedas no utilicen muchos recursos")
    @GetMapping("/page")
    public Page<RecetaResponseDTO> listarRecetas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortBy) {
        return recetaService.listar(page, size, sortBy);
    }

}
