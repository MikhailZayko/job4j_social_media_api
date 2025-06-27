package ru.job4j.socialmedia.validation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Schema(description = "Validation error response containing multiple violations")
public class ValidationErrorResponse {
    @Schema(description = "List of validation errors with field names and error messages")
    private final List<Violation> violations;
}
