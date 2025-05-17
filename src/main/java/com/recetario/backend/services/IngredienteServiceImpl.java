package com.recetario.backend.services;

import com.recetario.backend.dtos.IngredienteRequestDTO;
import com.recetario.backend.dtos.IngredienteResponseDTO;
import com.recetario.backend.entities.Ingrediente;
import com.recetario.backend.exception.ResourceNotFoundException;
import com.recetario.backend.repositories.IngredienteRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredienteServiceImpl implements IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    // Lista todos los ingredientes
    @Override
    public List<Ingrediente> listarIngredientes() {
        return ingredienteRepository.findAll();
    }

    // Crea nuevo ingrediente
    @Override
    public IngredienteResponseDTO crearIngrediente(IngredienteRequestDTO request) {
        Ingrediente ingrediente = Ingrediente.builder()
                .nombre(request.getNombre())
                .unidad(request.getUnidad())
                .costoUnitario(request.getCostoUnitario())
                .build();

        Ingrediente guardado = ingredienteRepository.save(ingrediente);

        return IngredienteResponseDTO.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .unidad(guardado.getUnidad())
                .costoUnitario(guardado.getCostoUnitario())
                .build();
    }

    // Modifica un ingrediente
    @Override
    public IngredienteResponseDTO actualizarIngrediente(Long id, IngredienteRequestDTO request) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingrediente no encontrado con ID: " + id));

        ingrediente.setNombre(request.getNombre());
        ingrediente.setUnidad(request.getUnidad());
        ingrediente.setCostoUnitario(request.getCostoUnitario());

        ingredienteRepository.save(ingrediente);

        return IngredienteResponseDTO.builder()
                .id(ingrediente.getId())
                .nombre(ingrediente.getNombre())
                .unidad(ingrediente.getUnidad())
                .costoUnitario(ingrediente.getCostoUnitario())
                .build();
    }

    // borra un ingrediente
    @Override
    public void eliminarIngrediente(Long id) {

        if (!ingredienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ingrediente no encontrado con ID: " + id);
        }
        ingredienteRepository.deleteById(id);
    }

}
