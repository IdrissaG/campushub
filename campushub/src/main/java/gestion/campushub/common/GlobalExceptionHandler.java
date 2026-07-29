package gestion.campushub.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Capte les erreurs de validation des DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErreurResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        // On extrait les messages sous forme de List<String> comme attendu par ton constructeur
        List<String> erreurs = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErreurResponse erreur = new ErreurResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                erreurs
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreur);
    }

    // Capte toutes les autres exceptions non gérées
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErreurResponse> handleGenericException(Exception ex) {
        ErreurResponse erreur = new ErreurResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of(ex.getMessage() != null ? ex.getMessage() : "Une erreur interne est survenue")
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erreur);
    }
}