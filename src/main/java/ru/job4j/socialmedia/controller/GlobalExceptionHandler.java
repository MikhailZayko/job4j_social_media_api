package ru.job4j.socialmedia.controller;

import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.job4j.socialmedia.exception.ExceptionResponse;
import ru.job4j.socialmedia.exception.NotFoundException;
import ru.job4j.socialmedia.validation.ValidationErrorResponse;
import ru.job4j.socialmedia.validation.Violation;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleDataIntegrityViolation(DataIntegrityViolationException e, HttpServletRequest request) {
        log.error("DataIntegrityViolation: {}", e.getMessage());
        return new ExceptionResponse(
                e.getMessage(),
                e.getClass().getSimpleName(),
                LocalDateTime.now().toString(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFound(NotFoundException e, HttpServletRequest request) {
        log.error("NotFoundException: {}", e.getMessage());
        return new ExceptionResponse(
                e.getMessage(),
                e.getClass().getSimpleName(),
                LocalDateTime.now().toString(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .toList();
        log.error(e.getLocalizedMessage());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .toList();
        log.error(e.getLocalizedMessage());
        return new ValidationErrorResponse(violations);
    }
}