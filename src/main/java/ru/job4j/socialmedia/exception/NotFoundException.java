package ru.job4j.socialmedia.exception;

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
