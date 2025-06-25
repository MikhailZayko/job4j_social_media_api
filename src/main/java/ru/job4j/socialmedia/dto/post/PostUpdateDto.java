package ru.job4j.socialmedia.dto.post;

import lombok.Data;

@Data
public class PostUpdateDto {
    private Integer id;
    private String title;
    private String content;
}
