package ru.job4j.socialmedia.dto.post;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostReadDto {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime created;
    private Integer userId;
}
