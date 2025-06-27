package ru.job4j.socialmedia.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserReadDto {
    @Schema(description = "Unique user ID", example = "123")
    private Integer id;

    @Schema(description = "Display name", example = "John Doe")
    private String username;

    @Schema(description = "Email address", example = "john@example.com")
    private String email;
}
