package ru.job4j.socialmedia.service.user;

import ru.job4j.socialmedia.dto.user.UserCreateDto;
import ru.job4j.socialmedia.dto.user.UserReadDto;
import ru.job4j.socialmedia.dto.user.UserUpdateDto;
import ru.job4j.socialmedia.model.User;

import java.util.Optional;

public interface UserService {

    UserReadDto create(UserCreateDto userCreateDto);

    Optional<UserReadDto> findById(Integer id);

    void update(UserUpdateDto userUpdateDto);

    void deleteById(Integer id);
}
