package ru.job4j.socialmedia.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse {
    private final String message;
    private final String type;
    private final String timestamp;
    private final String path;
}
