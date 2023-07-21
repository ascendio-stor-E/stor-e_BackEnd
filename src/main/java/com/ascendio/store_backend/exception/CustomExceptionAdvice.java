package com.ascendio.store_backend.exception;

import com.ascendio.store_backend.dto.exception.GenericExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionAdvice {

    @ExceptionHandler(value = {ImageSaveException.class})
    public ResponseEntity<GenericExceptionDto> imageSaveException(RuntimeException ex) {
        return ResponseEntity.internalServerError()
                .body(new GenericExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }
}
