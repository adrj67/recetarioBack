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
 * Repositorio para acceder a los datos de las recetas
 * @author adrj
 */
@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    /**
     * Buscar recetas por nombre
     * @param nombre
     * @return
     */
    List<Receta> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Buscar recetas por autor
     * @param autor
     * @return
     */
    List<Receta> findByAutorContainingIgnoreCase(String autor);

    /**
     * Buscar recetas por tipo
     * @param tipo
     * @return
     */
    List<Receta> findByTipoContainingIgnoreCase(String tipo);

    /**
     * Buscar un texto en las columnas de nombre, autor o tipo
     * @param texto
     * @return
     */
    @Query("SELECT r FROM Receta r WHERE " +
            "LOWER(r.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
            "LOWER(r.autor) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
            "LOWER(r.tipo) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Receta> buscarPorTexto(String texto);

    @Query("SELECT r FROM Receta r LEFT JOIN FETCH r.ingredientes WHERE r.id = :id")
    Optional<Receta> findByIdConIngredientes(@Param("id") Long id);

}
