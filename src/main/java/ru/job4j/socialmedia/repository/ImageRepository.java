package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmedia.model.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query("SELECT i FROM Image i WHERE i.post.id = :postId")
    List<Image> findByPostId(@Param("postId") Integer postId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Image i WHERE i.post.id = :postId")
    void deleteAllImagesByPostId(@Param("postId") Integer postId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Image i WHERE i.id = :imageId AND i.post.id = :postId")
    void deleteImageByPostId(@Param("imageId") Integer imageId, @Param("postId") Integer postId);
}
