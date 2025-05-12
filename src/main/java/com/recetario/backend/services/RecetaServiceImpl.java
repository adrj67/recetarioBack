package com.recetario.backend.services;

import com.recetario.backend.dtos.*;
import com.recetario.backend.entities.*;
import com.recetario.backend.exception.ResourceNotFoundException;
import com.recetario.backend.repositories.*;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RecetaServiceImpl implements RecetaService {

    private final RecetaRepository recetaRepository;
    private final IngredienteRepository ingredienteRepository;
    private final RecetaIngredienteRepository recetaIngredienteRepository;
    private final CloudinaryServiceImpl cloudinaryService;

    

    // Listar recetas
    @Override
    public List<RecetaResponseDTO> listarRecetas() {
        List<Receta> recetas = recetaRepository.findAll();
        return recetas.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    // Convierte receta a DTO
    private RecetaResponseDTO convertirADTO(Receta receta) {
    List<IngredienteDetalleDTO> ingredientesDTO = new ArrayList<>();

    if (receta.getIngredientes() != null) {
        ingredientesDTO = receta.getIngredientes().stream().map(ri -> {
            IngredienteDetalleDTO dto = new IngredienteDetalleDTO();
            dto.setNombre(ri.getIngrediente().getNombre());
            dto.setCantidad(ri.getCantidad());
            dto.setUnidad(ri.getIngrediente().getUnidad());
            dto.setCostoUnitario(ri.getIngrediente().getCostoUnitario());
            return dto;
        }).collect(Collectors.toList());
    }

    return RecetaResponseDTO.builder()
            .id(receta.getId())
            .nombre(receta.getNombre())
            .autor(receta.getAutor())
            .tipo(receta.getTipo())
            .porciones(receta.getPorciones())
            .procedimiento(receta.getProcedimiento())
            .tips(receta.getTips())
            .costoTotal(receta.getCostoTotal())
            .ingredientes(ingredientesDTO)
            .imagenUrl(receta.getImagenUrl())
            .build();
    }

    // Crea una receta nueva
    @Override
    @Transactional
    public RecetaResponseDTO crearReceta(RecetaRequestDTO request) {
    // 1. Crear y guardar la entidad Receta sin ingredientes
    Receta receta = Receta.builder()
            .nombre(request.getNombre())
            .autor(request.getAutor())
            .tipo(request.getTipo())
            .porciones(request.getPorciones())
            .procedimiento(request.getProcedimiento())
            .tips(request.getTips())
            .build();
    recetaRepository.save(receta); // Necesario para que tenga ID antes de asociar ingredientes

    // 2. Asociar ingredientes y calcular costo total
    double costoTotal = 0.0;
    List<RecetaIngrediente> ingredientesRelacionados = new ArrayList<>();

    for (IngredienteCantidadDTO i : request.getIngredientes()) {
        Ingrediente ingrediente = ingredienteRepository.findById(i.getIngredienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Ingrediente no encontrado con ID: " + i.getIngredienteId()));

        double subtotal = i.getCantidad() * ingrediente.getCostoUnitario();
        costoTotal += subtotal;

        RecetaIngrediente ri = new RecetaIngrediente();
        ri.setReceta(receta); // Asociación importante
        ri.setIngrediente(ingrediente);
        ri.setCantidad(i.getCantidad());
        ingredientesRelacionados.add(ri);
    }

    // Subir imagen si se incluye
    if (request.getImagen() != null && !request.getImagen().isEmpty()) {
        try {
            String imageUrl = cloudinaryService.uploadImage(request.getImagen());
            receta.setImagenUrl(imageUrl);
        } catch (IOException e) {
            throw new RuntimeException("Error al subir la imagen: ", e);
        }
    }

    // 3. Asociar la lista a la receta y guardar costo total
    receta.setIngredientes(ingredientesRelacionados);
    receta.setCostoTotal(costoTotal);
    recetaRepository.save(receta); // Guardar con ingredientes y costo

    // 4. Retornar respuesta
    return convertirADTO(receta);
    }

    // Modificar receta
    @Override
    @Transactional
    public RecetaResponseDTO actualizarReceta(Long id, @Valid RecetaRequestDTO request) {
        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receta no encontrada con id: " + id));

        receta.setNombre(request.getNombre());
        receta.setAutor(request.getAutor());
        receta.setTipo(request.getTipo());
        receta.setPorciones(request.getPorciones());
        receta.setProcedimiento(request.getProcedimiento());
        receta.setTips(request.getTips());

        List<RecetaIngrediente> nuevosIngredientes = request.getIngredientes().stream()
                .map(dto -> {
                    Ingrediente ingrediente = ingredienteRepository.findById(dto.getIngredienteId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Ingrediente no encontrado con id: " + dto.getIngredienteId()));

                    if (dto.getCantidad() <= 0) {
                        throw new IllegalArgumentException("La cantidad del ingrediente debe ser mayor a cero");
                    }

                    RecetaIngrediente ri = new RecetaIngrediente();
                    ri.setIngrediente(ingrediente);
                    ri.setCantidad(dto.getCantidad());
                    ri.setReceta(receta);
                    return ri;
                })
                .collect(Collectors.toList());

        receta.getIngredientes().clear();
        receta.getIngredientes().addAll(nuevosIngredientes);

        double costoTotal = nuevosIngredientes.stream()
                .mapToDouble(ri -> ri.getCantidad() * ri.getIngrediente().getCostoUnitario())
                .sum();
        receta.setCostoTotal(costoTotal);

        if (request.getImagen() != null && !request.getImagen().isEmpty()) {
            try {
                String imageUrl = cloudinaryService.uploadImage(request.getImagen());
                receta.setImagenUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir la imagen", e);
            }
        }


        return convertirADTO(recetaRepository.save(receta));
    }

    // Borrar receta
    @Override
    public void eliminarReceta(Long id) {
        if (!recetaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Receta no encontrada con ID: " + id);
        }
        recetaIngredienteRepository.deleteByRecetaId(id);
        recetaRepository.deleteById(id);
    }

    // Buscar receta por ID
    @Override
    public RecetaResponseDTO buscarPorId(Long id) {
    Receta receta = recetaRepository.findByIdConIngredientes(id)
        .orElseThrow(() -> new ResourceNotFoundException("Receta no encontrada con ID: " + id));
    return convertirADTO(receta);
    }

    // Buscar receta por nombre
    @Override
    public List<RecetaResponseDTO> buscarPorNombre(String nombre) {
        return recetaRepository.findByNombreContainingIgnoreCase(nombre)
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    // Buscar receta por autor
    @Override
    public List<RecetaResponseDTO> buscarPorAutor(String autor) {
        return recetaRepository.findByAutorContainingIgnoreCase(autor)
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    // Buscar receta por tipo
    @Override
    public List<RecetaResponseDTO> buscarPorTipo(String tipo) {
        return recetaRepository.findByTipoContainingIgnoreCase(tipo)
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    // Buscar receta por nombre+autor+tipo
    @Override
    public List<RecetaResponseDTO> buscarPorTexto(String texto) {
    return recetaRepository.buscarPorTexto(texto).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    // Listar recetas con paginacion
    @Override
    public Page<RecetaResponseDTO> listar(int page, int size, String sortBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
    Page<Receta> recetaPage = recetaRepository.findAll(pageable);

    return recetaPage.map(this::convertirADTO);
    }

}

