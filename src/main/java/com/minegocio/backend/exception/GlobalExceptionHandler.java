package com.minegocio.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.core.AuthenticationException;
import java.time.LocalDate;


//Esta anotación le dice a Spring que esta clase captura los errores de toda la API REST.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. MANEEJOR DEL ERROR 404 (No se encontró/ NotFound)
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

    // 2. MANEJO DEL ERROR 400 (Datos incorrectos o reglas de negocio rotas)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDate.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 3. MANEJO DEL ERROR 401 (Falta de permisos o credenciales inválidas)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDate.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // 4. MANEJO DEL ERROR 500 (Cualquier otro error inesperado del sistema)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDate.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Ocurrió un error interno en el servidor. Por favor, intente más tarde."
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 5. MANEJO DE ERRORES DE LOGIN (Contraseña o usuario incorrectos)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDate.now(),
                HttpStatus.UNAUTHORIZED.value(),           // 401
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),  // "Unauthorized"
                "El usuario o la contraseña son incorrectos." // Mensaje claro para el frontend
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


}
