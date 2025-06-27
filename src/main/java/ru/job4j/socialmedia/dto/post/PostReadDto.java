package ru.job4j.socialmedia.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostReadDto {
    @Schema(description = "Unique post ID", example = "456")
    private Integer id;

    @Schema(description = "Post title", example = "Interesting Topic")
    private String title;

    @Schema(description = "Post content", example = "Detailed content here...")
    private String content;

    @Schema(description = "Creation timestamp", example = "2023-10-15T14:30:00")
    private LocalDateTime created;

    @Schema(description = "Author's user ID", example = "123")
    private Integer userId;
}
