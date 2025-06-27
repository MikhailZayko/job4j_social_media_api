package ru.job4j.socialmedia.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "Error response structure")
public class ExceptionResponse {
    @Schema(description = "Error message", example = "User not found")
    private final String message;

    @Schema(description = "Exception type", example = "NotFoundException")
    private final String type;

    @Schema(description = "Timestamp of error", example = "2023-10-15T14:30:00.12345")
    private final String timestamp;

    @Schema(description = "Request path", example = "/api/user/999")
    private final String path;
}
