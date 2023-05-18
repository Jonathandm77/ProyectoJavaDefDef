package principal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        // Código para manejar la excepción y generar una respuesta personalizada
        String mensaje = "El método de solicitud no es compatible";
        return new ResponseEntity<>(mensaje, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
