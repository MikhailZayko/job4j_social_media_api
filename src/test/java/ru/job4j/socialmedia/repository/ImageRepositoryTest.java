package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Image;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private Post post;

    @BeforeEach
    void setUp() {
        imageRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
        User user = new User();
        user.setUsername("user");
        user.setEmail("user@example.com");
        user.setPassword("pass");
        userRepository.save(user);
        post = new Post();
        post.setTitle("Post Title");
        post.setContent("Content");
        post.setUser(user);
        postRepository.save(post);
    }

    @Test
    void whenSaveImageThenFindByPost() {
        Image image = new Image();
        image.setFilePath("/images/test.jpg");
        image.setPost(post);
        imageRepository.save(image);
        Image foundImage = imageRepository.findById(image.getId()).orElseThrow();
        assertThat(foundImage.getFilePath()).isEqualTo("/images/test.jpg");
        assertThat(foundImage.getPost().getTitle()).isEqualTo("Post Title");
    }
}