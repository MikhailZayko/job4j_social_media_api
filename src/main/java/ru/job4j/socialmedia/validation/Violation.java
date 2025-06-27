package ru.job4j.socialmedia.validation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Single validation error detail")
public class Violation {
    @Schema(description = "Name of the invalid field", example = "email")
    private final String fieldName;

    @Schema(description = "Validation error message", example = "Email should be valid")
    private final String message;
}
