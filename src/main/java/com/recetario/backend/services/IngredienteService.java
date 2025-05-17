package com.recetario.backend.services;

import java.util.List;

import com.recetario.backend.dtos.IngredienteRequestDTO;
import com.recetario.backend.dtos.IngredienteResponseDTO;
import com.recetario.backend.entities.Ingrediente;

public interface IngredienteService {

    List<Ingrediente> listarIngredientes();

    IngredienteResponseDTO crearIngrediente(IngredienteRequestDTO request);

    IngredienteResponseDTO actualizarIngrediente(Long id, IngredienteRequestDTO request);

    void eliminarIngrediente(Long id);
}
