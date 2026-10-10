package com.minegocio.backend.exception;

// Heredamos de RuntimeException para que sea una excepción que no obligue
// a llenar el código de molestos 'try-catch' por todos lados.
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
