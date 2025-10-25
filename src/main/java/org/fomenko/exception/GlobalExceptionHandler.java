package org.fomenko.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserByIdNotFoundException.class)
    public ResponseEntity<String> handleUserByIdNotFound(UserByIdNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(UserByNameNotFoundException.class)
    public ResponseEntity<String> handleUserByNameNotFound(UserByNameNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<String> handleUserUpdate(UserUpdateException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}