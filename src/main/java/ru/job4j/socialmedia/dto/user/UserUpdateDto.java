package ru.job4j.socialmedia.dto.user;

import lombok.Data;

@Data
public class UserUpdateDto {
    private Integer id;
    private String username;
    private String email;
}
