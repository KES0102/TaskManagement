package endo.kes.web;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalExceptionHandler {

    static private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        log.error("handleException", e);

        var errorDto = new ErrorResponseDto("Internal server error", e.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(500).body(errorDto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFound(
            EntityNotFoundException e
    ){
        log.error("Handle entityNotFoundException", e);

        var errorDto = new ErrorResponseDto(
                "entity not found",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(404).body(errorDto);
    }



    @ExceptionHandler(exception = {
            IllegalArgumentException.class,
            IllegalStateException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponseDto> handleBadRequest(Exception e) {
        log.error("handleBadRequest", e);

        var errorDto = new ErrorResponseDto("Bad request", e.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(400).body(errorDto);
    }

}
