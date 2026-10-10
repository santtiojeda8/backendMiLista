package com.minegocio.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;


//Esta anotación le dice a Spring que esta clase captura los errores de toda la API REST.
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound( ResourceNotFoundException resourceNotFoundException) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDate.now(),
                HttpStatus.NOT_FOUND.value(),   // 404
                HttpStatus.NOT_FOUND.getReasonPhrase(), //"Not Found"
                resourceNotFoundException.getMessage()  //El mensaje personalizado que enviemos nosotros cuando usamos el error
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
