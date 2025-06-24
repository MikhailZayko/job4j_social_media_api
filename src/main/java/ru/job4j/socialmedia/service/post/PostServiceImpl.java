package ru.job4j.socialmedia.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Image;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.repository.PostRepository;
import ru.job4j.socialmedia.service.image.ImageService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final ImageService imageService;

    @Override
    public Post createPostWithoutImages(Post post) {
        return postRepository.save(post);
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
    public void updateTitleAndContent(Post post) {
        postRepository.updateTitleAndContent(post.getId(), post.getTitle(), post.getContent());
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
        postRepository.deleteByPostId(postId);
    }
}
