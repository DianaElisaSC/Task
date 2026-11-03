package com.devtiro.task.controller;

import com.devtiro.task.domain.dto.ErrorResponseDto;
import com.devtiro.task.exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(
            MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Validation failed");
        return new ResponseEntity<>(new ErrorResponseDto(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleTaskNotFound(TaskNotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}