package ru.job4j.socialmedia.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostUpdateDto {
    @NotNull(message = "ID must not be null")
    @Positive(message = "ID must be 1 or more")
    private Integer id;

    @NotBlank(message = "Title must not be blank")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Content must not be blank")
    @Size(min = 10, max = 500, message = "Content must be between 10 and 500 characters")
    private String content;
}
