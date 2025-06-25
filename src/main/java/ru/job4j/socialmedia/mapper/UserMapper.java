package ru.job4j.socialmedia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.job4j.socialmedia.dto.user.UserCreateDto;
import ru.job4j.socialmedia.dto.user.UserReadDto;
import ru.job4j.socialmedia.dto.user.UserUpdateDto;
import ru.job4j.socialmedia.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserReadDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserCreateDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateFromDto(UserUpdateDto dto, @MappingTarget User entity);
}
