package ru.job4j.socialmedia.dto.userpost;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.job4j.socialmedia.dto.post.PostReadDto;

import java.util.List;

@Data
public class UserPostsDto {
    @Schema(description = "User ID", example = "123")
    private Integer userId;

    @Schema(description = "Username", example = "john_doe")
    private String username;

    @Schema(description = "List of user's posts")
    private List<PostReadDto> posts;
}
