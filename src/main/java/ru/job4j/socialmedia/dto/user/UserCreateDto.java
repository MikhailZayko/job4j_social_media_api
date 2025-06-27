package ru.job4j.socialmedia.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDto {
    @Schema(description = "Unique username", example = "john_doe", minLength = 3, maxLength = 12)
    @NotBlank(message = "Username must not be blank")
    @Size(min = 3, max = 12, message = "Username must be between 3 and 12 characters")
    private String username;

    @Schema(description = "Valid email address", example = "john@example.com")
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Password (min 6 characters)", example = "securePass123", minLength = 6, maxLength = 20)
    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;
}
