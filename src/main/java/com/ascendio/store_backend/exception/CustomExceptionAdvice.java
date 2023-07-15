package com.ascendio.store_backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionAdvice {

    @ExceptionHandler(value = {StoryNotFoundException.class})
    public ResponseEntity<Void> storyNotFoundException(RuntimeException ex) {
        return ResponseEntity.notFound().build();
    }
}
