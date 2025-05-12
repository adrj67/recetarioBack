package com.recetario.backend.services;

import java.util.List;

import com.recetario.backend.dtos.RecetaRequestDTO;
import com.recetario.backend.dtos.RecetaResponseDTO;
import org.springframework.data.domain.Page;


public interface RecetaService {

    List<RecetaResponseDTO> listarRecetas();

    RecetaResponseDTO crearReceta(RecetaRequestDTO request);
    
    RecetaResponseDTO actualizarReceta(Long id, RecetaRequestDTO request);
    
    void eliminarReceta(Long id);

    RecetaResponseDTO buscarPorId(Long id);

    List<RecetaResponseDTO> buscarPorNombre(String nombre);

    List<RecetaResponseDTO> buscarPorAutor(String autor);

    List<RecetaResponseDTO> buscarPorTipo(String tipo);

    // Busca en nombre+autor+tipo
    List<RecetaResponseDTO> buscarPorTexto(String texto);

    public Page<RecetaResponseDTO> listar(int page, int size, String sortBy);


}

