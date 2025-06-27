package ru.job4j.socialmedia.dto.userpost;

import lombok.Data;
import ru.job4j.socialmedia.dto.post.PostReadDto;

import java.util.List;

@Data
public class UserPostsDto {
    private Integer userId;
    private String username;
    private List<PostReadDto> posts;
}
