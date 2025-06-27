package ru.job4j.socialmedia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmedia.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUserId(Integer userId);

    List<Post> findByCreatedBetween(LocalDateTime start, LocalDateTime end);

    Page<Post> findByOrderByCreatedDesc(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p SET p.title = :title, p.content = :content WHERE p.id = :postId")
    void updateTitleAndContent(
            @Param("postId") Integer postId,
            @Param("title") String title,
            @Param("content") String content
    );

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Post p WHERE p.id = :postId")
    void deleteByPostId(@Param("postId") Integer postId);

    @Query("""
        SELECT p FROM Post p
        WHERE p.user.id IN (
            SELECT s.target.id FROM Subscription s
            WHERE s.subscriber.id = :userId
        )
        ORDER BY p.created DESC
    """)
    Page<Post> findPostsBySubscribedUsers(@Param("userId") Integer userId, Pageable pageable);

    @EntityGraph(attributePaths = "user")
    List<Post> findByUserIdIn(List<Integer> userIds);
}
