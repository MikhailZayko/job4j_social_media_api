package ru.job4j.socialmedia.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exception thrown when requested resource is not found")
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Resource not found");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Class<?> cl, Integer id) {
        super("%s with id = %d not found".formatted(cl.getSimpleName(), id));
    }
}
