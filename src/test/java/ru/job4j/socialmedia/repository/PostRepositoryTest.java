package ru.job4j.socialmedia.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Image;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.model.Subscription;
import ru.job4j.socialmedia.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private EntityManager entityManager;

    private User author1;

    private User author2;

    private Post post1;

    private Post post2;

    private Post post3;

    private Post post4;

    @BeforeEach
    void setUp() {
        author1 = new User();
        author1.setUsername("author1");
        author1.setEmail("author1@example.com");
        author1.setPassword("pass");
        entityManager.persist(author1);
        author2 = new User();
        author2.setUsername("author2");
        author2.setEmail("author2@example.com");
        author2.setPassword("pass");
        entityManager.persist(author2);
        LocalDateTime now = LocalDateTime.now();
        post1 = createPost("Title 1", "Content 1", author1, now.minusDays(3));
        post2 = createPost("Title 2", "Content 2", author1, now.minusDays(2));
        post3 = createPost("Title 3", "Content 3", author2, now.minusDays(1));
        post4 = createPost("Title 4", "Content 4", author1, now);
    }

    private Post createPost(String title, String content, User user, LocalDateTime created) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);
        post.setCreated(created);
        return postRepository.save(post);
    }

    private void createImage(String filePath, Post post) {
        Image image = new Image();
        image.setFilePath(filePath);
        image.setPost(post);
        imageRepository.save(image);
    }

    private void createSubscription(User subscriber, User target) {
        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setTarget(target);
        entityManager.persist(subscription);
    }

    @Test
    void whenSavePostThenFindByUser() {
        Post foundPost = postRepository.findById(post1.getId()).orElseThrow();
        assertThat(foundPost.getTitle()).isEqualTo("Title 1");
        assertThat(foundPost.getUser().getUsername()).isEqualTo("author1");
    }

    @Test
    void whenFindByUserIdThenReturnUserPosts() {
        List<Post> author1Posts = postRepository.findByUserId(author1.getId());
        List<Post> author2Posts = postRepository.findByUserId(author2.getId());
        assertThat(author1Posts)
                .hasSize(3)
                .extracting(Post::getTitle)
                .containsExactlyInAnyOrder("Title 1", "Title 2", "Title 4");
        assertThat(author2Posts)
                .hasSize(1)
                .extracting(Post::getTitle)
                .containsExactly("Title 3");
    }

    @Test
    void whenFindByCreatedBetweenThenReturnPostsInDateRange() {
        LocalDateTime start = post1.getCreated().plusMinutes(1);
        LocalDateTime end = post4.getCreated().minusMinutes(1);
        List<Post> result = postRepository.findByCreatedBetween(start, end);
        assertThat(result)
                .hasSize(2)
                .extracting(Post::getTitle)
                .containsExactlyInAnyOrder("Title 2", "Title 3");
    }

    @Test
    void whenFindByOrderByCreatedDescThenReturnPagedAndSorted() {
        Page<Post> firstPage = postRepository.findByOrderByCreatedDesc(Pageable.ofSize(2));
        assertThat(firstPage.getContent())
                .hasSize(2)
                .extracting(Post::getTitle)
                .containsExactly("Title 4", "Title 3");
        assertThat(firstPage.getTotalElements()).isEqualTo(4);
        assertThat(firstPage.getTotalPages()).isEqualTo(2);
        assertThat(firstPage.hasNext()).isTrue();
        Page<Post> secondPage = postRepository.findByOrderByCreatedDesc(firstPage.nextPageable());
        assertThat(secondPage.getContent())
                .hasSize(2)
                .extracting(Post::getTitle)
                .containsExactly("Title 2", "Title 1");
    }

    @Test
    void whenDeletePostWithImagesThenPostAndImagesDeleted() {
        createImage("/images/post1-1.jpg", post1);
        createImage("/images/post1-2.jpg", post1);
        List<Image> imagesBeforeDelete = imageRepository.findByPostId(post1.getId());
        assertThat(imagesBeforeDelete)
                .hasSize(2)
                .extracting(Image::getFilePath)
                .containsExactlyInAnyOrder(
                        "/images/post1-1.jpg",
                        "/images/post1-2.jpg"
                );
        postRepository.deleteByPostId(post1.getId());
        assertThat(postRepository.findById(post1.getId())).isEmpty();
        List<Image> imagesAfterDelete = imageRepository.findByPostId(post1.getId());
        assertThat(imagesAfterDelete).isEmpty();
        assertThat(postRepository.findById(post2.getId())).isPresent();
        assertThat(postRepository.findById(post3.getId())).isPresent();
        assertThat(postRepository.findById(post4.getId())).isPresent();
    }

    @Test
    void whenUpdateTitleAndContentThenPostUpdated() {
        postRepository.updateTitleAndContent(
                post1.getId(),
                "Updated Title",
                "Updated Content"
        );
        Post updatedPost = postRepository.findById(post1.getId()).orElseThrow();
        assertThat(updatedPost.getTitle()).isEqualTo("Updated Title");
        assertThat(updatedPost.getContent()).isEqualTo("Updated Content");
    }

    @Test
    void whenFindPostsBySubscribedUsersThenReturnSubscribedPosts() {
        User subscriber = author1;
        User target = author2;
        createSubscription(subscriber, target);
        Page<Post> result = postRepository.findPostsBySubscribedUsers(
                subscriber.getId(),
                Pageable.unpaged()
        );
        assertThat(result.getContent())
                .hasSize(1)
                .extracting(Post::getTitle)
                .containsExactly("Title 3");
    }
}