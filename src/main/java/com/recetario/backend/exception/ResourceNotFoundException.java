/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.recetario.backend.exception;

/**
 *
 * @author adrj
 */
public class ResourceNotFoundException extends RuntimeException{
     // Constructor con mensaje
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }

    // Constructor con mensaje y causa
    public ResourceNotFoundException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // Constructor sin argumentos
    public ResourceNotFoundException() {
        super("Recurso no encontrado");
    }
}
