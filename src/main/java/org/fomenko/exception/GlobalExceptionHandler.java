package org.fomenko.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleNotFound(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    // можно добавить другие обработчики
    // например @ExceptionHandler(IllegalArgumentException.class)
}