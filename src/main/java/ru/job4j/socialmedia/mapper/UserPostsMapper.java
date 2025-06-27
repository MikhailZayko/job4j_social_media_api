package ru.job4j.socialmedia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.socialmedia.dto.userpost.UserPostsDto;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.model.Post;

import java.util.List;

@Mapper(componentModel = "spring", uses = PostMapper.class)
public interface UserPostsMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "posts", source = "posts")
    UserPostsDto toDto(User user, List<Post> posts);
}
