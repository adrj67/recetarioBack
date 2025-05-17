/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.recetario.backend.dtos;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author adrj
 */

@Schema(description = "Receta con ingredientes y costo total")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecetaResponseDTO {

    private Long id;
    @Schema(description = "Nombre de la receta", example = "Tortilla de papas")
    private String nombre;
    @Schema(description = "Autor de la receta", example = "Arguiñano, Abuela Berta, etc.")
    private String autor;
    @Schema(description = "Tipo de receta", example = "Entrada, Postre, Sopa, etc.")
    private String tipo;
    private Integer porciones;
    private String procedimiento;
    private String tips;

    private List<IngredienteDetalleDTO> ingredientes;

    @Schema(description = "Costo total de la receta", example = "150.0")
    private Double costoTotal;

    @Schema(description = "Imagen representativa de la receta", example = "La imagen se guarda en Cloudinary")
    private String imagenUrl; // solo la URL, no el archivo
}
