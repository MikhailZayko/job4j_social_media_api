package ru.job4j.socialmedia.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserUpdateDto {
    @Schema(description = "User ID to update", example = "123")
    @Positive
    @NotNull(message = "ID must not be null")
    private Integer id;

    @Schema(description = "New username", example = "updated_name", minLength = 3, maxLength = 12)
    @NotBlank(message = "Username must not be blank")
    @Size(min = 3, max = 12, message = "Username must be between 3 and 50 characters")
    private String username;

    @Schema(description = "New email", example = "new@example.com")
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;
}
