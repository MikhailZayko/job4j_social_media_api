package ru.job4j.socialmedia.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.dto.post.PostCreateDto;
import ru.job4j.socialmedia.dto.post.PostReadDto;
import ru.job4j.socialmedia.dto.post.PostUpdateDto;
import ru.job4j.socialmedia.mapper.PostMapper;
import ru.job4j.socialmedia.model.Image;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.repository.PostRepository;
import ru.job4j.socialmedia.service.image.ImageService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final ImageService imageService;

    private final PostMapper postMapper;

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
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
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
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        postRepository.deleteByPostId(postId);
    }

    @Override
    public Optional<PostReadDto> findById(Integer id) {
        return postRepository.findById(id)
                .map(postMapper::toReadDto);
    }
}
