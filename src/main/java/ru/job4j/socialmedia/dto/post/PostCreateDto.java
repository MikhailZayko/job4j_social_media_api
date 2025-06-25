package ru.job4j.socialmedia.dto.post;

import lombok.Data;

@Data
public class PostCreateDto {
    private String title;
    private String content;
    private Integer userId;
}
