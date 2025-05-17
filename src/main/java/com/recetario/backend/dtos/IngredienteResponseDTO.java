package com.recetario.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredienteResponseDTO {
    private Long id;
    @Schema(description = "Nombre del ingrdiente", example = "Harina")
    private String nombre;
    @Schema(description = "Unidad de medida", example = "Kilo")
    private String unidad;
     @Schema(description = "Precio del ingrediente por unidad", example = "25.50")
    private Double costoUnitario;
}
