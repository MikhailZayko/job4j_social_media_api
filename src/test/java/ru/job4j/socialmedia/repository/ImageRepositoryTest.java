package ru.job4j.socialmedia.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Image;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private EntityManager entityManager;

    private Post post;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("user");
        user.setEmail("user@example.com");
        user.setPassword("pass");
        entityManager.persist(user);
        post = new Post();
        post.setTitle("Post Title");
        post.setContent("Content");
        post.setUser(user);
        entityManager.persist(post);
    }

    private Image createImage(String filePath, Post post) {
        Image image = new Image();
        image.setFilePath(filePath);
        image.setPost(post);
        return imageRepository.save(image);
    }

    @Test
    void whenSaveImageThenFindByPost() {
        Image image = createImage("/images/test.jpg", post);
        Image foundImage = imageRepository.findById(image.getId()).orElseThrow();
        assertThat(foundImage.getFilePath()).isEqualTo("/images/test.jpg");
        assertThat(foundImage.getPost().getTitle()).isEqualTo("Post Title");
    }

    @Test
    void whenDeleteAllImagesByPostIdThenImagesDeleted() {
        createImage("/images/test1.jpg", post);
        createImage("/images/test2.jpg", post);
        imageRepository.deleteAllImagesByPostId(post.getId());
        List<Image> images = imageRepository.findByPostId(post.getId());
        assertThat(images).isEmpty();
    }

    @Test
    void whenDeleteImageByPostIdThenSpecificImageDeleted() {
        Image image1 = createImage("/images/test1.jpg", post);
        createImage("/images/test2.jpg", post);
        imageRepository.deleteImageByPostId(image1.getId(), post.getId());
        List<Image> images = imageRepository.findByPostId(post.getId());
        assertThat(images)
                .hasSize(1)
                .extracting(Image::getFilePath)
                .containsExactly("/images/test2.jpg");
    }

    @Test
    void whenDeleteImageWithWrongPostIdThenNotDeleted() {
        Image image = createImage("/images/test.jpg", post);
        imageRepository.deleteImageByPostId(image.getId(), 999);
        assertThat(imageRepository.findById(image.getId())).isPresent();
    }
}