/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.recetario.backend.dtos;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class RecetaRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Agregue un autor")
    private String autor;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @Min(value = 1, message = "Las porciones deben ser al menos 1")
    private Integer porciones;

    private String procedimiento;
    
    private String tips;

    private List<IngredienteCantidadDTO> ingredientes;

    private MultipartFile imagen; // imagen como archivo
}
