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
public class RecetaController {

    private final RecetaService recetaService;

    // Listar todas las recetas
    @GetMapping
    public ResponseEntity<List<RecetaResponseDTO>> listarRecetas() {
        return ResponseEntity.ok(recetaService.listarRecetas());
    }

    // Crear Receta
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecetaResponseDTO> crearReceta(@ModelAttribute RecetaRequestDTO request) {
        RecetaResponseDTO respuesta = recetaService.crearReceta(request);
        return ResponseEntity.ok(respuesta);
    }


    // Modificar receta
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecetaResponseDTO> actualizarReceta(
            @PathVariable Long id,
            @ModelAttribute RecetaRequestDTO request) {
        RecetaResponseDTO actualizada = recetaService.actualizarReceta(id, request);
        return ResponseEntity.ok(actualizada);
}

    
    // Borrar receta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Long id) {
    recetaService.eliminarReceta(id);
    return ResponseEntity.noContent().build();
    }

    // Buscar receta por ID
    @GetMapping("/{id}")
    public ResponseEntity<RecetaResponseDTO> obtenerRecetaPorId(@PathVariable Long id) {
        RecetaResponseDTO receta = recetaService.buscarPorId(id);
        return ResponseEntity.ok(receta);
    }
    
    // Buscar receta por nombre
    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<RecetaResponseDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(recetaService.buscarPorNombre(nombre));
    }

    // Buscar receta por autor
    @GetMapping("/buscar/autor")
    public ResponseEntity<List<RecetaResponseDTO>> buscarPorAutor(@RequestParam String autor) {
        return ResponseEntity.ok(recetaService.buscarPorAutor(autor));
    }

    // Buscar receta por tipo de receta
    @GetMapping("/buscar/tipo")
    public ResponseEntity<List<RecetaResponseDTO>> buscarPorTipo(@RequestParam String tipo) {
        return ResponseEntity.ok(recetaService.buscarPorTipo(tipo));
    }

    // busca por nombre+autor+tipo 
    @GetMapping("/buscar")
    public ResponseEntity<List<RecetaResponseDTO>> buscarPorTexto(@RequestParam String texto) {
    return ResponseEntity.ok(recetaService.buscarPorTexto(texto));
    }

    // Mostrar por paginacion
    @GetMapping ("/page")
    public Page<RecetaResponseDTO> listarRecetas(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "nombre") String sortBy
) {
    return recetaService.listar(page, size, sortBy);
}


}
