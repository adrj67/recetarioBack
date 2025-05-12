/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.recetario.backend.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author adrj
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaResponseDTO {

    private Long id;
    private String nombre;
    private String autor;
    private String tipo;
    private Integer porciones;
    private String procedimiento;
    private String tips;
    
    private List<IngredienteDetalleDTO> ingredientes;
    private Double costoTotal;
    
    private String imagenUrl; // solo la URL, no el archivo
}
