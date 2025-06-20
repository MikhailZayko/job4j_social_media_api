package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User author;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        author = new User();
        author.setUsername("author");
        author.setEmail("author@example.com");
        author.setPassword("pass");
        userRepository.save(author);
    }

    @Test
    void whenSavePostThenFindByUser() {
        Post post = new Post();
        post.setTitle("Test Title");
        post.setContent("Test Content");
        post.setUser(author);
        postRepository.save(post);
        Post foundPost = postRepository.findById(post.getId()).orElseThrow();
        assertThat(foundPost.getTitle()).isEqualTo("Test Title");
        assertThat(foundPost.getUser().getUsername()).isEqualTo("author");
    }
}