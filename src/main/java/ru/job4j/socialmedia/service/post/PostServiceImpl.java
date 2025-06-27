package ru.job4j.socialmedia.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.dto.post.PostCreateDto;
import ru.job4j.socialmedia.dto.post.PostReadDto;
import ru.job4j.socialmedia.dto.post.PostUpdateDto;
import ru.job4j.socialmedia.dto.userpost.UserPostsDto;
import ru.job4j.socialmedia.exception.NotFoundException;
import ru.job4j.socialmedia.mapper.PostMapper;
import ru.job4j.socialmedia.mapper.UserPostsMapper;
import ru.job4j.socialmedia.model.Image;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.PostRepository;
import ru.job4j.socialmedia.repository.UserRepository;
import ru.job4j.socialmedia.service.image.ImageService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final ImageService imageService;

    private final PostMapper postMapper;

    private final UserPostsMapper userPostsMapper;

    @Override
    public PostReadDto create(PostCreateDto postCreateDto) {
        Post post = postMapper.fromCreateDto(postCreateDto);
        Post savedPost = postRepository.save(post);
        return postMapper.toReadDto(savedPost);
    }

    @Override
    public Post createPostWithImages(Post post, List<Image> images) {
        Post savedPost = postRepository.save(post);
        images.forEach(image -> {
            image.setPost(post);
            imageService.save(image);
        });
        return savedPost;
    }

    @Override
    public void update(PostUpdateDto postUpdateDto) {
        Post existingPost = postRepository.findById(postUpdateDto.getId())
                .orElseThrow(() -> new NotFoundException(Post.class, postUpdateDto.getId()));
        postMapper.updateFromUpdateDto(postUpdateDto, existingPost);
        postRepository.save(existingPost);
    }

    @Override
    public void insertImage(Post post, Image image) {
        image.setPost(post);
        imageService.save(image);
    }

    @Override
    public void deleteImageByPost(Image image, Post post) {
        imageService.deleteImageByPostId(image, post.getId());
    }

    @Override
    public void deleteById(Integer postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(Post.class, postId));
        postRepository.deleteByPostId(postId);
    }

    @Override
    public Optional<PostReadDto> findById(Integer id) {
        return postRepository.findById(id)
                .map(postMapper::toReadDto);
    }

    @Override
    public List<UserPostsDto> findUsersWithPostsByUserIds(List<Integer> userIds) {
        Map<Integer, User> users = userRepository.findAllById(userIds).stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        Map<Integer, List<Post>> postsByUser = postRepository.findByUserIdIn(userIds).stream()
                .collect(Collectors.groupingBy(post -> post.getUser().getId()));
        return userIds.stream()
                .map(id -> {
                    User user = users.get(id);
                    if (user == null) {
                        throw new NotFoundException(User.class, id);
                    }
                    List<Post> posts = postsByUser.getOrDefault(id, Collections.emptyList());
                    return userPostsMapper.toDto(user, posts);
                })
                .toList();
    }
}
