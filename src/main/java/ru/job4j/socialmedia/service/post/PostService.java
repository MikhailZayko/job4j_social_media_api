package ru.job4j.socialmedia.service.post;

import ru.job4j.socialmedia.dto.post.PostCreateDto;
import ru.job4j.socialmedia.dto.post.PostReadDto;
import ru.job4j.socialmedia.dto.post.PostUpdateDto;
import ru.job4j.socialmedia.dto.userpost.UserPostsDto;
import ru.job4j.socialmedia.model.Image;
import ru.job4j.socialmedia.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    PostReadDto create(PostCreateDto postCreateDto);

    Post createPostWithImages(Post post, List<Image> images);

    void update(PostUpdateDto postUpdateDto);

    void insertImage(Post post, Image image);

    void deleteImageByPost(Image image, Post post);

    void deleteById(Integer postId);

    Optional<PostReadDto> findById(Integer postId);

    List<UserPostsDto> findUsersWithPostsByUserIds(List<Integer> userIds);
}
