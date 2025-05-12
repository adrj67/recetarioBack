/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.recetario.backend.repositories;

import com.recetario.backend.entities.Ingrediente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author adrj
 */

public interface IngredienteRepository extends JpaRepository <Ingrediente, Long> {

    // Buscar ingredientes por el nombre ignorando mayusculas
    List<Ingrediente> findByNombreContainingIgnoreCase(String nombre);

}
