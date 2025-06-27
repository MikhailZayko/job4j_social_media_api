package ru.job4j.socialmedia.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.job4j.socialmedia.exception.NotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public void catchDataIntegrityViolationException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Map<String, String> details = new HashMap<>();
        details.put("message", e.getMessage());
        details.put("type", String.valueOf(e.getClass()));
        details.put("timestamp", String.valueOf(LocalDateTime.now()));
        details.put("path", request.getRequestURI());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(details));
        log.error(e.getLocalizedMessage());
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public void catchNotFoundException(NotFoundException e,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
        Map<String, String> details = new HashMap<>();
        details.put("message", e.getMessage());
        details.put("type", e.getClass().getSimpleName());
        details.put("timestamp", LocalDateTime.now().toString());
        details.put("path", request.getRequestURI());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(details));
        log.error("NotFoundException: {}", e.getMessage());
    }
}