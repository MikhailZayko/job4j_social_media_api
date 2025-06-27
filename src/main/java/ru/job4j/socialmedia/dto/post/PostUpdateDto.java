package ru.job4j.socialmedia.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostUpdateDto {
    @Schema(description = "Post ID to update", example = "456")
    @NotNull(message = "ID must not be null")
    @Positive(message = "ID must be 1 or more")
    private Integer id;

    @Schema(description = "Updated title", example = "Updated Title", minLength = 3, maxLength = 100)
    @NotBlank(message = "Title must not be blank")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Schema(description = "Updated content", example = "New content here...", minLength = 10, maxLength = 500)
    @NotBlank(message = "Content must not be blank")
    @Size(min = 10, max = 500, message = "Content must be between 10 and 500 characters")
    private String content;
}
