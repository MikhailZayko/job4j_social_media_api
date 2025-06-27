package ru.job4j.socialmedia.dto.user;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserUpdateDto {
    @Positive
    @NotNull(message = "ID must not be null")
    private Integer id;

    @NotBlank(message = "Username must not be blank")
    @Size(min = 3, max = 12, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;
}
