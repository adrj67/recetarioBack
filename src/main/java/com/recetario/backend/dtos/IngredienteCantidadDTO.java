/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.recetario.backend.dtos;

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
public class IngredienteCantidadDTO {
    private Long ingredienteId;
    private Double cantidad;
}
