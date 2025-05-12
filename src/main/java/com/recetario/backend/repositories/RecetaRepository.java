/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.recetario.backend.repositories;

import com.recetario.backend.entities.Receta;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author adrj
 */

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    // Buscar recetas por nombre
    List<Receta> findByNombreContainingIgnoreCase(String nombre);

    // Buscar recetas por autor
    List<Receta> findByAutorContainingIgnoreCase(String autor);

    // Buscar recetas por tipo
    List<Receta> findByTipoContainingIgnoreCase(String tipo);

    // Buscar por nombre+autor+tipo
    @Query("SELECT r FROM Receta r WHERE " +
       "LOWER(r.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
       "LOWER(r.autor) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
       "LOWER(r.tipo) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Receta> buscarPorTexto(String texto);

    
    @Query("SELECT r FROM Receta r LEFT JOIN FETCH r.ingredientes WHERE r.id = :id")
    Optional<Receta> findByIdConIngredientes(@Param("id") Long id);


}
