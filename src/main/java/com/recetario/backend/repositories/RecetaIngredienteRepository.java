/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.recetario.backend.repositories;

import com.recetario.backend.entities.RecetaIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author adrj
 */
public interface RecetaIngredienteRepository extends JpaRepository<RecetaIngrediente, Long> {

    // Borrar los ingredientes de la reta a burrar
    @Transactional
    void deleteByRecetaId(Long recetaId);

}
