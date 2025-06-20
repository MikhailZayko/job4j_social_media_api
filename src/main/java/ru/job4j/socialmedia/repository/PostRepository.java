package ru.job4j.socialmedia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmedia.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUserId(Integer userId);

    List<Post> findByCreatedBetween(LocalDateTime start, LocalDateTime end);

    Page<Post> findByOrderByCreatedDesc(Pageable pageable);
}
